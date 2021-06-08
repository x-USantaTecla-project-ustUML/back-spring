package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPersistence {
    Account read(String email);
    void create(Account account);
    Account update(Account account);
}
