package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.WithMembersMember;
import com.usantatecla.ustumlserver.domain.persistence.WithMembersMemberPersistence;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandType;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

abstract class WithMembersMemberInterpreter extends MemberInterpreter {

    @Autowired
    protected WithMembersMemberPersistence withMembersMemberPersistence;

    protected WithMembersMemberInterpreter(Account account, Member member) {
        super(account, member);
    }

    @Override
    public boolean isInvalidAddKeys(Command command) {
        return super.isInvalidAddKeys(command) && !command.has(Command.MEMBERS);
    }

    @Override
    public void deleteCommandSections(Command command) {
        super.deleteCommandSections(command);
        WithMembersMember withMembersMember = (WithMembersMember) this.member;
        List<Member> members = new ArrayList<>();
        for (Command projectCommand : command.getCommands(Command.MEMBERS)) {
            members.add(withMembersMember.deleteMember(projectCommand.getMemberName()));
        }
        this.member = this.withMembersMemberPersistence.deleteMembers(withMembersMember, members);
    }

    @Override
    public boolean isInvalidDeleteKeys(Command command) {
        return super.isInvalidAddKeys(command) && !command.has(Command.MEMBERS);
    }

    @Override
    public void modifyCommandSections(Command command) {
        super.modifyCommandSections(command);
        WithMembersMember withMembersMember = (WithMembersMember) this.member;
        for (Command memberCommand : command.getCommands(Command.MEMBERS)) {
            withMembersMember.modify(memberCommand.getMemberName(), memberCommand.getString(Command.SET));
        }
    }

    @Override
    public boolean isInvalidModifyKeys(Command command) {
        return super.isInvalidAddKeys(command) && !command.has(Command.MEMBERS);
    }

    @Override
    public Member open(Command command) {
        String name = command.getString(CommandType.OPEN.getName());
        Member member = ((WithMembersMember) this.member).find(name);
        if (member == null) {
            throw new ServiceException(ErrorMessage.MEMBER_NOT_FOUND, name);
        }
        return member;
    }

}
