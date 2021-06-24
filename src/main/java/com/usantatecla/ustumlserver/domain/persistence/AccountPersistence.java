package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPersistence {
    void create(Account account);
    Account read(String email);
}
