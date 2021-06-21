package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.MemberParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
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
            Member member = memberParser.get(memberCommand);
            this.addRelations(memberCommand, member);
            pakage.add(memberParser.get(memberCommand));
        }
        this.addRelations(command, this.member);
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
        Package pakage = (Package) this.member;
        List<Member> members = new ArrayList<>();
        for (Command projectCommand : command.getCommands(Command.MEMBERS)) {
            members.add(pakage.deleteMember(projectCommand.getMemberName()));
        }
        this.member = this.packagePersistence.deleteRelations(this.member, this.deleteRelations(command));
        this.member = this.packagePersistence.deleteMembers(pakage, members);
    }

}
