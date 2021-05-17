package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;

import java.util.ArrayList;
import java.util.List;

public class PackageParser extends MemberParser {

    @Override
    public Member get(Command command) {
        Package pakage = new Package(this.getName(command), new ArrayList<>());
        if(command.has(PackageService.MEMBERS_KEY)) {
            this.addMembers(pakage, command.getCommands(PackageService.MEMBERS_KEY));
        }
        return pakage;
    }

    void addMembers(Package pakage, List<Command> memberCommands) {
        for (Command memberCommand : memberCommands) {
            MemberType memberType = memberCommand.getMemberType();
            Member member = memberType.create().get(memberCommand);
            if (!pakage.find(member.getName())) {
                pakage.add(member);
            } else {
                throw new CommandParserException(Error.MEMBER_ALREADY_EXISTS, member.getName());
            }
        }
    }

    @Override
    public PackageParser copy() {
        return new PackageParser();
    }

}
