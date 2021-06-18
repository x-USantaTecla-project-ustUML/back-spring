package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.builders.AccountBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ProjectBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.TestSeeder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
/*
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
        Account account = new AccountBuilder(TestSeeder.ACCOUNT).build();
        AccountEntity accountEntity = (AccountEntity) this.memberEntityDeleter.delete(account, List.of(project));
        assertTrue(accountEntity.getProjectEntities().contains(null));
    }

}*/
