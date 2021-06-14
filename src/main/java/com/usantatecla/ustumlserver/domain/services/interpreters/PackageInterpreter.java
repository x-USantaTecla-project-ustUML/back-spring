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
            MemberParser memberParser = memberCommand.getMemberType().modify();
            List<Member> members = new ArrayList<>();
            for(Member member: pakage.getMembers()){
                if(member.isPackage() && member.getName().equals("y")){//TODO command.getString(MemberParser.SET_KEY)
                    members = ((Package)member).getMembers();
                }
            }
            pakage.modify(memberParser.get(memberCommand), memberParser.getModifiedMember(memberCommand), members);
        }
        this.modifyRelations(command);
        this.member = this.packagePersistence.update(pakage);
    }

    @Override
    Member find(String name) {
        return ((Package) this.member).find(name);
    }

}
