package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.AccountPersistenceMongodb;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MemberInterpreter {

    @Autowired
    protected AccountPersistenceMongodb accountPersistence;

    protected Member member;

    protected MemberInterpreter(Member member) {
        this.member = member;
    }

    public abstract void add(Command command);

    public abstract void accept(InterpreterVisitor interpreterVisitor);

    public Member getMember() {
        return this.member;
    }
}
