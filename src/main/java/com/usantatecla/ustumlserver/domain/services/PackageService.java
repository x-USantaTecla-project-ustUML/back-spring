package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageService extends MemberService {

    private List<Member> members;
    private PackagePersistence packagePersistence;

    @Autowired
    public PackageService(PackagePersistence packagePersistence) {
        this.packagePersistence = packagePersistence;
    }

    public PackageService() {
        this.members = new ArrayList<>();
    }

    public Package get(Command command) {
        Package pakage = this.packagePersistence.read("name");
        this.addMembers(command, pakage);
        this.packagePersistence.update(pakage);
        return pakage;
    }

    private Package addMembers(Command command, Package pakage) {
        for (Command memberCommand : command.getMembers()) {
            MemberType memberType = memberCommand.getMemberType();
            CommandType commandType = command.getCommandType();
            if (commandType == CommandType.ADD) {
                Member member = memberType.create().add(memberCommand);
                if (!pakage.find(member.getName())) {
                    pakage.add(member);
                } else {
                    throw new CommandParserException(Error.MEMBER_ALREADY_EXISTS, member.getName());
                }
            }
        }
        return pakage;
    }

    @Override
    Package add(Command command) {
        this.parseName(command);
        return this.addMembers(command, new Package(this.name, this.members));
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
