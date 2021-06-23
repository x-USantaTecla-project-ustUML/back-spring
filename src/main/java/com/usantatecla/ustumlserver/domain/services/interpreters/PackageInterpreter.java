package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.WithMembersMemberPersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.MemberParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PackageInterpreter extends WithMembersInterpreter {

    @Autowired
    private WithMembersMemberPersistence withMembersMemberPersistence;

    public PackageInterpreter(Account account, Member member) {
        super(account, member);
    }

    @Override
    public void addCommandSections(Command command) {
        super.addCommandSections(command);
        Package pakage = (Package) this.member;
        for (Command memberCommand : command.getCommands(Command.MEMBERS)) {
            MemberParser memberParser = memberCommand.getMemberType().create(this.account);
            pakage.add(memberParser.get(memberCommand));
        }
    }

    @Override
    public boolean isInvalidAddKeys(Command command) {
        return super.isInvalidAddKeys(command) && !command.has(Command.MEMBERS);
    }

    @Override
    public void deleteCommandSections(Command command) {
        super.deleteCommandSections(command);
        Package pakage = (Package) this.member;
        List<Member> members = new ArrayList<>();
        for (Command projectCommand : command.getCommands(Command.MEMBERS)) {
            members.add(pakage.deleteMember(projectCommand.getMemberName()));
        }
        this.member = this.withMembersMemberPersistence.deleteMembers(pakage, members);
    }

    @Override
    public boolean isInvalidDeleteKeys(Command command) {
        return super.isInvalidAddKeys(command) && !command.has(Command.MEMBERS);
    }

    @Override
    public void modifyCommandSections(Command command) {
        super.modifyCommandSections(command);
        Package pakage = (Package) this.member;
        for (Command memberCommand : command.getCommands(Command.MEMBERS)) {
            pakage.modify(memberCommand.getMemberName(), memberCommand.getString(Command.SET));
        }
    }

    @Override
    public boolean isInvalidModifyKeys(Command command) {
        return super.isInvalidAddKeys(command) && !command.has(Command.MEMBERS);
    }

}
