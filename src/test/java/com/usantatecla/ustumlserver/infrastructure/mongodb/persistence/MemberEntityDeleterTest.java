package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.builders.AccountBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ProjectBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.TestSeeder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void testGivenMemberEntityDeleterWhenDeleteProjectThenReturn() {
        Project project = new ProjectBuilder(TestSeeder.PROJECT).id("id").build();
        Project project2 = Project.builder().id("id").name("project2").members(new ArrayList<>())
                .relations(new ArrayList<>()).build();
        Account account = new AccountBuilder(TestSeeder.ACCOUNT).projects(project2).build();
        AccountEntity accountEntity = (AccountEntity) this.memberEntityDeleter.delete(account
                , Arrays.asList(project, project2), new ArrayList<>());
        assertTrue(accountEntity.getProjectEntities().contains(null));
    }

}
