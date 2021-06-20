package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.builders.AccountBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ProjectBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.TestSeeder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AccountEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ProjectEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class MemberEntityUpdaterTest {

    @Autowired
    private TestSeeder testSeeder;
    @Autowired
    private MemberEntityUpdater memberEntityUpdater;

    @BeforeEach
    void beforeEach() {
        this.testSeeder.initialize();
    }

    @Test
    void testGivenMemberEntityUpdaterWhenUpdateNotExistentAccountThenThrowException() {
        Account account = Account.builder().id("notExist").name("notExist").email("notExist").build();
        assertThrows(PersistenceException.class, () -> this.memberEntityUpdater.update(account));
    }

    @Test
    void testGivenMemberEntityUpdaterWhenUpdateAccountThenReturn() {
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .project().name("new")
                .build();
        AccountEntity expected = new AccountEntity(account);
        assertThat(this.memberEntityUpdater.update(account), is(expected));
    }

    @Test
    void testGivenMemberEntityUpdaterWhenUpdateNotExistentProjectThenThrowException() {
        Project project = new ProjectBuilder().id("notExist").name("notExist").build();
        assertThrows(PersistenceException.class, () -> this.memberEntityUpdater.update(project));
    }

    @Test
    void testGivenMemberEntityUpdaterWhenUpdateProjectThenReturn() {
        Project project = new ProjectBuilder(TestSeeder.PROJECT)
                .build();
        ProjectEntity expected = new ProjectEntity(project);
        assertThat(this.memberEntityUpdater.update(project), is(expected));
    }

    @Test
    void testGivenMemberEntityUpdaterWhenUpdateNotExistentClassThenThrowException() {
        Class clazz = new ClassBuilder().id("notExist").name("notExist").build();
        assertThrows(PersistenceException.class, () -> this.memberEntityUpdater.update(clazz));
    }

    @Test
    void testGivenMemberEntityUpdaterWhenUpdateClassThenReturn() {
        Class clazz = new ClassBuilder(TestSeeder.CLASS)
                .attribute()._private().type("Type").name("attr")
                .method()._public().type("Type").name("meth").parameter()
                .use().target(TestSeeder.PACKAGE)
                .build();
        ClassEntity expected = new ClassEntity(clazz);
        assertThat(this.memberEntityUpdater.update(clazz), is(expected));
    }

    @Test
    void testGivenMemberEntityUpdaterWhenUpdateClassWithTargetRelationNotExistentThenThrowException() {
        Class clazz = new ClassBuilder(TestSeeder.CLASS)
                .use().target(new ClassBuilder().build())
                .build();
        assertThrows(PersistenceException.class, () -> this.memberEntityUpdater.update(clazz));
    }

}
