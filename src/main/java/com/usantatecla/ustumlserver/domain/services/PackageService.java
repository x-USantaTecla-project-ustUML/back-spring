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
    public PackageService(PackagePersistence packagePersistence, Member member) {
        super(member);
        this.packagePersistence = packagePersistence;
    }

    public PackageService(Member member) {
        super(member);
    }

    @Override
    Package add(Command command) {
        this.addMembers((Package) this.member, command.getCommands(PackageService.MEMBERS_KEY));
        return (Package) this.member;
    }

    private void addMembers(Package pakage, List<Command> memberCommands) {
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
    public Member get(Command command) {
        Package pakage = new Package(this.getName(command), new ArrayList<>());
        if(command.has(PackageService.MEMBERS_KEY)) {
            this.addMembers(pakage, command.getCommands(PackageService.MEMBERS_KEY));
        }
        return pakage;
    }

    @Override
    public MemberService copy() {
        return new PackageService();
    }
}
