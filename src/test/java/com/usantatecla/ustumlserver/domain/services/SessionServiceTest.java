package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.TestSeeder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestConfig
@Disabled
class SessionServiceTest {

    @Mock
    private TokenManager tokenManager;
    @InjectMocks
    @Autowired
    private SessionService sessionService;
    @Autowired
    private TestSeeder seeder;

    @BeforeEach
    void beforeEach() {
        this.seeder.initialize();
    }

    @Test
    void testGivenSessionServiceWhenReadThenReturn() {
        this.seeder.seedOpen();
        List<Member> expected = List.of(new PackageBuilder()
                .id(Seeder.PROJECT_ID)
                .pakage(TestSeeder.PACKAGE)
                .classes(TestSeeder.CLASS)
                .build());
        when(this.tokenManager.user(anyString())).thenReturn("a");
        assertThat(this.sessionService.read(TestSeeder.SESSION_ID), is(expected));
    }

    @Test
    void testGivenSessionServiceWhenReadNotExistThenReturn() {
        List<Member> expected = List.of(Seeder.ACCOUNT);
        when(this.tokenManager.user(anyString())).thenReturn("a");
        assertThat(this.sessionService.read("id"), is(expected));
    }

    @Test
    void testGivenSessionServiceWhenUpdateThenReturn() {
        this.seeder.seedOpen();
        this.sessionService.update(TestSeeder.SESSION_ID, new ArrayList<>());
        when(this.tokenManager.user(anyString())).thenReturn("a");
        assertThat(this.sessionService.read(TestSeeder.SESSION_ID), is(new ArrayList<>()));
    }

    @Test
    void testGivenSessionServiceWhenDeleteThenReturn() {
        this.seeder.seedOpen();
        this.sessionService.delete(TestSeeder.SESSION_ID);
        assertThrows(PersistenceException.class, () -> this.sessionService.update(Seeder.PROJECT_ID,
                new ArrayList<>()));
    }

    @Test
    void testGivenSessionServiceWhenUpdateNotExistThenThrow() {
        assertThrows(PersistenceException.class, () -> this.sessionService.update("id", new ArrayList<>()));
    }

}
