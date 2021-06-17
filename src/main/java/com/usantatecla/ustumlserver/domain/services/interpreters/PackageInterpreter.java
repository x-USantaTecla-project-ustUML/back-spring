package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.domain.services.parsers.MemberParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PackageInterpreter extends WithMembersInterpreter {

    @Autowired
    private PackagePersistence packagePersistence;

    public PackageInterpreter(Account account, Member member) {
        super(account, member);
    }

    @Override
    public void add(Command command) {
        super.add(command);
        Package pakage = (Package) this.member;
        for (Command memberCommand : command.getCommands(Command.MEMBERS)) {
            MemberParser memberParser = memberCommand.getMemberType().create();
            pakage.add(memberParser.get(memberCommand));
        }
        this.addRelations(command);
        this.member = this.packagePersistence.update(pakage);
    }

    @Override
    public void modify(Command command) {
        super.modify(command);
        Package pakage = (Package) this.member;
        for (Command memberCommand : command.getCommands(Command.MEMBERS)) {
            pakage.modify(memberCommand.getMemberName(), memberCommand.getString(Command.SET));
        }
        this.modifyRelations(command);
        this.member = this.packagePersistence.update(pakage);
    }

    @Override
    public void delete(Command command) {
        super.delete(command);
        List<Member> members = new ArrayList<>();
        for (Command memberCommand : command.getCommands(Command.MEMBERS)) {
            Member member = ((Package) this.member).find(memberCommand.getMemberName());
            if (member == null) {
                throw new ServiceException(ErrorMessage.MEMBER_NOT_FOUND, memberCommand.getMemberName());
            }
            members.add(member);
        }
        this.member = this.packagePersistence.deleteMembers((Package) this.member, members);
        this.member = this.packagePersistence.deleteRelations(this.member, this.getRelationsToDelete(command));
    }

}
