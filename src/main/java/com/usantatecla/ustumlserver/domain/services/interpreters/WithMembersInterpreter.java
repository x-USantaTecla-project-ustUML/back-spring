package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandType;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

abstract class WithMembersInterpreter extends MemberInterpreter {

    protected WithMembersInterpreter(Member member) {
        super(member);
    }

    @Override
    public Member open(Command command) {
        String name = command.getString(CommandType.OPEN.getName());
        Member member = this.find(name);
        if (member == null) {
            throw new ServiceException(ErrorMessage.MEMBER_NOT_FOUND, name);
        }
        return member;
    }

    abstract Member find(String name);

}
