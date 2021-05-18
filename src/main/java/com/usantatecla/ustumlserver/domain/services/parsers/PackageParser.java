package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.services.Command;
import com.usantatecla.ustumlserver.domain.services.Error;

import java.util.ArrayList;
import java.util.List;

public class PackageParser extends MemberParser {

    private List<Member> members;

    public PackageParser() {
        this.members = new ArrayList<>();
    }

    @Override
    public Member get(Command command) {
        this.parseName(command);
        Package pakage = new Package(this.name, new ArrayList<>());
        if (command.has(PackageParser.MEMBERS_KEY)) {
            this.addMembers(pakage, command);
        }
        return pakage;
    }

    public void addMembers(Package pakage, Command command) {
        for (Command memberCommand : command.getCommands(PackageParser.MEMBERS_KEY)) {
            MemberType memberType = memberCommand.getMemberType();
            Member member = memberType.create().get(memberCommand);
            if (pakage.find(member.getName()) == null) {
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
