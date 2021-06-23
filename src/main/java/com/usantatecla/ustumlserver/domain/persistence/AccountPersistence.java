package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountPersistence {
    Account read(String email);
    void create(Account account);
    Account deleteMembers(Account account, List<Member> members);
}
