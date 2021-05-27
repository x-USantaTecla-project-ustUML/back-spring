package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.PackageBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.TestSeeder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
public class SessionServiceTest {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private TestSeeder seeder;

    @BeforeEach
    void beforeEach() {
        this.seeder.initialize();
    }

    /*@Test
    void testGivenSessionServiceWhenReadThenReturn() {
        this.seeder.seedOpen();
        List<Member> expected = List.of(new PackageBuilder()
                .id(Seeder.PROJECT_ID)
                .pakage(TestSeeder.PACKAGE)
                .classes(TestSeeder.CLASS)
                .build());
        assertThat(this.sessionService.read(TestSeeder.SESSION_ID), is(expected));
    }

    @Test
    void testGivenSessionServiceWhenReadNotExistThenReturn() {
        List<Member> expected = List.of(new PackageBuilder().id(Seeder.PROJECT_ID).build());
        assertThat(this.sessionService.read("id"), is(expected));
    }

    @Test
    void testGivenSessionServiceWhenUpdateThenReturn() {
        this.seeder.seedOpen();
        this.sessionService.update(TestSeeder.SESSION_ID, new ArrayList<>());
        assertThat(this.sessionService.read(TestSeeder.SESSION_ID), is(new ArrayList<>()));
    }

    @Test
    void testGivenSessionServiceWhenUpdateNotExistThenThrow() {
        assertThrows(PersistenceException.class, () -> this.sessionService.update("id", new ArrayList<>()));
    }*/

}
