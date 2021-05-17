package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageService extends MemberService {

    static final String MEMBERS_KEY = "members";

    private PackagePersistence packagePersistence;

    @Autowired
    public PackageService(PackagePersistence packagePersistence) {
        this.packagePersistence = packagePersistence;
    }

    public PackageService() {
    }

    public Package get(Command command) {
        Package pakage = this.packagePersistence.read("name");
        if (command.getCommandType() == CommandType.ADD) {
            this.addMembers(pakage, command.getMembers());
        }
        this.packagePersistence.update(pakage);
        return pakage;
    }

    private void addMembers(Package pakage, List<Command> memberCommands) {
        for (Command memberCommand : memberCommands) {
            MemberType memberType = memberCommand.getMemberType();
            Member member = memberType.create().add(memberCommand);
            if (!pakage.find(member.getName())) {
                pakage.add(member);
            } else {
                throw new CommandParserException(Error.MEMBER_ALREADY_EXISTS, member.getName());
            }

        }
    }

    @Override
    Package add(Command command) {
        this.parseName(command);
        Package pakage = new Package(this.name, new ArrayList<>());
        if(command.has(PackageService.MEMBERS_KEY)) {
            this.addMembers(pakage, command.getCommands(PackageService.MEMBERS_KEY));
        }
        return pakage;
    }

    private void parseName(Command command) {
        String name = command.getMemberName();
        if (Package.matchesName(name)) {
            this.name = name;
        } else {
            throw new CommandParserException(Error.INVALID_NAME, name);
        }
    }

    @Override
    public MemberService copy() {
        return new PackageService();
    }
}
