package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.UserPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInterpreter extends MemberInterpreter{

    @Autowired
    private UserPersistence userPersistence;

    public UserInterpreter(Member member) {
        super(member);
    }

    @Override
    public void add(Command command) {

    }

    @Override
    public void accept(InterpreterVisitor interpreterVisitor) {

    }
}
