package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PackageService {

    private Package pakage;

    public PackageService() {
        this.pakage = new Package("name", new ArrayList<>());
    }

    public Package get(Command command) {
        CommandType commandType = command.getCommandType();
        for (Command memberCommand : command.getMembers()) {
            MemberType memberType = memberCommand.getMemberType();
            if (commandType == CommandType.ADD) {
                Member member = memberType.create().add(memberCommand);
                if (!this.pakage.find(member.getName())) {
                    this.pakage.add(member);
                } else {
                    throw new CommandParserException(Error.MEMBER_ALREADY_EXISTS, member.getName());
                }
            }
        }
        return this.pakage;
    }
}
