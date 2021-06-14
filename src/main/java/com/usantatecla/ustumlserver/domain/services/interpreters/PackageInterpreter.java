package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Relation;
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
            pakage.add(memberParser.get(memberCommand));
        }
        this.addRelations(command);
        this.member = this.packagePersistence.update(pakage);
    }

    @Override
    public void delete(Command command) {
        super.delete(command);
        List<Member> members = new ArrayList<>();
        List<Relation> relations = new ArrayList<>();
        for (Command projectCommand : command.getCommands(Command.MEMBERS)) {
            members.add(((Package)this.member).findMember(projectCommand.getMemberName()));
        }
        for (Command relationCommand : command.getCommands(Command.RELATIONS)) {
            relations.add(this.member.findRelation(relationCommand.getRelationTargetName()));
        }
        this.member = this.packagePersistence.delete((Package)this.member, members, relations);
    }

    @Override
    Member find(String name) {
        return ((Package) this.member).findMember(name);
    }

}
