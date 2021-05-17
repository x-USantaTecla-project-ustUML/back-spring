package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import org.springframework.beans.factory.annotation.Autowired;

public class PackageService extends MemberService {

    static final String MEMBERS_KEY = "members";

    @Autowired
    private PackagePersistence packagePersistence;

    public PackageService(Member member) {
        super(member);
    }

    @Override
    Package add(Command command) {
        Package pakage = (Package) this.member;
        new PackageParser().addMembers(pakage, command.getCommands(PackageService.MEMBERS_KEY));
        this.packagePersistence.update(pakage);
        return pakage;
    }

}
