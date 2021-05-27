package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Role;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TokenManager tokenManager;

    @Test
    void testGivenUserServiceWhenCreateUserThenEmailAlreadyExist() {
        Account account = Account.builder().email("a").password("a").role(Role.AUTHENTICATED).build();
        assertThrows(PersistenceException.class, () -> this.accountService.create(account));
    }

}
