package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.persistence.SessionPersistence;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@TestConfig
class SessionServiceTest {

    @Autowired
    private TestSeeder testSeeder;
    @Autowired
    private SessionPersistence sessionPersistence;
    private SessionService sessionService;

    @BeforeEach
    void beforeEach() {
        this.testSeeder.initialize();
        this.sessionService = spy(new SessionService(this.sessionPersistence));
    }

    @Test
    void testGivenSessionServiceWhenReadNotExistentSessionThenReturn() {
        doReturn(Seeder.ACCOUNT.getEmail()).when(this.sessionService).getCurrentUserEmail();
        assertThat(this.sessionService.read("notExist"), is(List.of(Seeder.ACCOUNT)));
    }

    @Test
    void testGivenSessionServiceWhenReadSessionThenReturn() {
        doReturn(Seeder.ACCOUNT.getEmail()).when(this.sessionService).getCurrentUserEmail();
        assertThat(this.sessionService.read(TestSeeder.SESSION_ID), is(new ArrayList<>()));
    }

    @Test
    void testGivenSessionServiceWhenAddWithWrongSessionIDThenReturn() {
        assertThrows(PersistenceException.class, () -> this.sessionPersistence.add("notExist", TestSeeder.CLASS));
    }

    @Test
    void testGivenSessionServiceWhenAddThenReturn() {
        this.sessionService.add(TestSeeder.SESSION_ID, TestSeeder.CLASS);
        doReturn(Seeder.ACCOUNT.getEmail()).when(this.sessionService).getCurrentUserEmail();
        assertThat(this.sessionService.read(TestSeeder.SESSION_ID), is(List.of(TestSeeder.CLASS)));
    }

    @Test
    void testGivenSessionServiceWhenDeleteWithWrongSessionIDThenReturn() {
        assertThrows(PersistenceException.class, () -> this.sessionPersistence.delete("notExist", TestSeeder.CLASS));
    }

    @Test
    void testGivenSessionServiceWhenDeleteNonExistentMemberThenThrows() {
        assertThrows(PersistenceException.class, () -> this.sessionPersistence.delete(TestSeeder.SESSION_ID, TestSeeder.CLASS));
    }

    @Test
    void testGivenSessionServiceWhenDeleteMemberThenReturn() {
        this.sessionService.add(TestSeeder.SESSION_ID, TestSeeder.CLASS);
        this.sessionService.delete(TestSeeder.SESSION_ID, TestSeeder.CLASS);
        doReturn(Seeder.ACCOUNT.getEmail()).when(this.sessionService).getCurrentUserEmail();
        assertThat(this.sessionService.read(TestSeeder.SESSION_ID), is(new ArrayList<>()));
    }

    @Test
    void testGivenSessionServiceWhenDeleteNotExistentSessionThenNothingThrows() {
        assertDoesNotThrow(() -> this.sessionPersistence.delete("notExist"));
    }

    @Test
    void testGivenSessionServiceWhenDeleteSessionThenNothingThrows() {
        assertDoesNotThrow(() -> this.sessionPersistence.delete(TestSeeder.SESSION_ID));
    }

}
