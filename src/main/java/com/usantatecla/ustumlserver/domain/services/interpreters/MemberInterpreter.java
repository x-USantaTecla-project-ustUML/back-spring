package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.AccountPersistence;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MemberInterpreter {

    @Autowired
    protected AccountPersistence accountPersistence;

    protected Member member;

    protected MemberInterpreter(Member member) {
        this.member = member;
    }

    public abstract void add(Command command);

    public void _import(Command command) {
        throw new ServiceException(ErrorMessage.IMPORT_NOT_ALLOWED);
    }

    public Member open(Command command) {
        throw new ServiceException(ErrorMessage.OPEN_NOT_ALLOWED);
    }

    public Member getMember() {
        return this.member;
    }
}
