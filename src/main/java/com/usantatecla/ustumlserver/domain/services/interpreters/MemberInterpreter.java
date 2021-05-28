package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;

public abstract class MemberInterpreter {

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
