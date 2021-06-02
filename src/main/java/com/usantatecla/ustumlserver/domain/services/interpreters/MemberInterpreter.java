package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.AccountPersistenceMongodb;
import org.springframework.beans.factory.annotation.Autowired;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

public abstract class MemberInterpreter {

    @Autowired
    protected AccountPersistenceMongodb accountPersistence;

    protected Member member;

    protected MemberInterpreter(Member member) {
        this.member = member;
    }

    public abstract void add(Command command);

    public void _import(String url) {
        throw new ServiceException(ErrorMessage.IMPORT_NOT_ALLOWED);
    }

    public Member open(Command command) {
        throw new ServiceException(ErrorMessage.OPEN_NOT_ALLOWED);
    }

    public abstract void accept(InterpreterVisitor interpreterVisitor);

    public Member getMember() {
        return this.member;
    }
}
