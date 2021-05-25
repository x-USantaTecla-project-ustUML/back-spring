package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

public class PackageParser extends MemberParser {

    static final String MEMBERS_KEY = "members";

    protected List<Member> members;

    public PackageParser() {
        this.members = new ArrayList<>();
    }

    @Override
    public Member get(Command command) {
        this.parseName(command);
        Package pakage = new Package(this.name, this.members);
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
                throw new ParserException(ErrorMessage.MEMBER_ALREADY_EXISTS, member.getName());
            }
        }
    }

    @Override
    public PackageParser copy() {
        return new PackageParser();
    }

}
