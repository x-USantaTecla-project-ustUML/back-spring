package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.AccountDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.SessionDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AccountEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.SessionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestConfig
public class SessionPersistenceMongodbTest {

    private static final String EMAIL = "a";

    @Autowired
    @Mock
    private SessionDao sessionDao;
    @Autowired
    @Mock
    private AccountDao accountDao;
    @Autowired
    @InjectMocks
    private SessionPersistenceMongodb sessionPersistenceMongodb;

    @Autowired
    private Seeder seeder;

    @BeforeEach
    void beforeEach() {
        this.seeder.initialize();
    }

    @Test
    void  testGivenSessionPersistenceWhenReadThenReturn() {
        SessionEntity expected = SessionEntity.builder().memberEntities(Collections.singletonList(PackageEntity.builder().build())).build();
        when(this.sessionDao.findBySessionId(anyString())).thenReturn(expected);
        assertThat(this.sessionPersistenceMongodb.read(Seeder.PROJECT_ID, SessionPersistenceMongodbTest.EMAIL), is(expected.getMembers()));
    }

    @Test
    void  testGivenSessionPersistenceWhenReadThenReturnEmpty() {
        AccountEntity account = new AccountEntity();
        when(this.sessionDao.findBySessionId(anyString())).thenReturn(null);
        when(this.accountDao.findByEmail(anyString())).thenReturn(account);
        assertThat(this.sessionPersistenceMongodb.read("id", "email"),
                is(Collections.singletonList(account.toAccount())));
    }

    @Test
    void testGivenSessionPersistenceWhenDeleteThenDo() {
        SessionEntity expected = SessionEntity.builder().memberEntities(Collections.singletonList(PackageEntity.builder().build())).build();
        when(this.sessionDao.findBySessionId(anyString())).thenReturn(expected, null);
        this.sessionPersistenceMongodb.delete(Seeder.PROJECT_ID);
        assertThrows(PersistenceException.class, () -> this.sessionPersistenceMongodb.update(Seeder.PROJECT_ID,
                new ArrayList<>()));
    }

}
