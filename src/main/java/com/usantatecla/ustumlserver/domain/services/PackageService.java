package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PackageService {

    //private Package pakage;
    private PackagePersistence packagePersistence;

    @Autowired
    public PackageService(PackagePersistence packagePersistence) {
        //this.pakage = new Package("name", new ArrayList<>());
        this.packagePersistence = packagePersistence;
    }

    public Package get(Command command) {
        Package pakage = this.packagePersistence.read("name");
        CommandType commandType = command.getCommandType();
        for (Command memberCommand : command.getMembers()) {
            MemberType memberType = memberCommand.getMemberType();
            if (commandType == CommandType.ADD) {
                Member member = memberType.create().add(memberCommand);
                if (!pakage.find(member.getName())) {
                    pakage.add(member);
                } else {
                    throw new CommandParserException(Error.MEMBER_ALREADY_EXISTS, member.getName());
                }
            }
        }
        this.packagePersistence.update(pakage);
        return pakage;
    }

}
