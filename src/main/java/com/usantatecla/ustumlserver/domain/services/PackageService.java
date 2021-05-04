package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;

import java.util.ArrayList;

public class PackageService {

    private Package pakage;

    public PackageService() {
        this.pakage = new Package("name", new ArrayList<>());
    }

    public Member get(Command command) {
        CommandType commandType = command.getCommandType();
        if (commandType.isNull()) {
            throw new CommandParserException(Error.COMMAND_NOT_FOUND.getDetail());
        }
        for (Command member : command.getMembers()) {
            MemberType memberType = member.getMemberType();
            if (memberType.isNull()) {
                throw new CommandParserException(Error.MEMBER_NOT_FOUND.getDetail());
            }
            if (commandType == CommandType.ADD) {
                this.pakage.add(memberType.create().add(member));
            }
        }
        return this.pakage;
    }
}
