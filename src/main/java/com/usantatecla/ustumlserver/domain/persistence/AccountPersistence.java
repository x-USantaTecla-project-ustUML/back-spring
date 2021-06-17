package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountPersistence extends MemberPersistence {
    Account read(String email);
    void create(Account account);
    Account update(Account account);
    Account deleteMembers(Account account, List<Member> members);
}
