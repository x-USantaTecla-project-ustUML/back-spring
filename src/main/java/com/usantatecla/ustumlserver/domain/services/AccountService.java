package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.persistence.AccountPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountPersistence accountPersistence;
    private TokenManager tokenManager;

    @Autowired
    public AccountService(AccountPersistence accountPersistence, TokenManager tokenManager) {
        this.accountPersistence = accountPersistence;
        this.tokenManager = tokenManager;
    }

    public Account read(String email) {
        return this.accountPersistence.read(email);
    }

    public String login(String email) {
        Account account = this.accountPersistence.read(email);
        return tokenManager.createToken(account.getEmail(), account.getRole().name());
    }

    public void create(Account account) {
        this.accountPersistence.create(account);
    }

}
