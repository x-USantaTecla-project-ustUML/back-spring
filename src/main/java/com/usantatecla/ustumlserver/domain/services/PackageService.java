package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Package;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PackageService {

    private Package pakage;

    public PackageService() {
        this.pakage = new Package("name", new ArrayList<>());
    }

    public Member get(Command command) {
        CommandType commandType = command.getCommandType();
        for (Command member : command.getMembers()) {
            MemberType memberType = member.getMemberType();
            if (commandType == CommandType.ADD) {
                this.pakage.add(memberType.create().add(member));
            }
        }
        return this.pakage;
    }
}
