package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.builders.AccountBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ProjectBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.TestSeeder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
public class MemberEntityDeleterTest {

    @Autowired
    private TestSeeder testSeeder;
    @Autowired
    private MemberEntityDeleter memberEntityDeleter;

    @BeforeEach
    void beforeEach() {
        this.testSeeder.initialize();
    }

    @Test
    void testGivenMemberEntityDeleterWhenDeleteNotExistentAccountThenThrowException() {
        Account account = new AccountBuilder().name("notExist").email("notExist").build();
        assertThrows(PersistenceException.class,
                () -> this.memberEntityDeleter.delete(account, new ArrayList<>(), new ArrayList<>()));
    }

    @Test
    void testGivenMemberEntityDeleterWhenDeleteProjectThenReturn() {
        Project project = new ProjectBuilder().id(Seeder.PROJECT_ID).name("new").build();
        Account account = new AccountBuilder(Seeder.ACCOUNT).projects(project).build();
        AccountEntity expected = new AccountEntity(new AccountBuilder(Seeder.ACCOUNT).build());
        assertThat(this.memberEntityDeleter.delete(account
                , Collections.singletonList(project), new ArrayList<>()), is(expected));
    }

}
