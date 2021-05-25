package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;

public class ClassInterpreter extends MemberInterpreter {

    public ClassInterpreter(Member member) {
        super(member);
    }

    @Override
    public void add(Command command) {

    }

    @Override
    public void accept(InterpreterVisitor interpreterVisitor) {
        interpreterVisitor.visit(this);
    }

}
