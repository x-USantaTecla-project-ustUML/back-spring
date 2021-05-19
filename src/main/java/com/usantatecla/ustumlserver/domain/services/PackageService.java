package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.CommandParserException;
import com.usantatecla.ustumlserver.domain.services.parsers.PackageParser;
import org.springframework.beans.factory.annotation.Autowired;

public class PackageService extends MemberService {

    @Autowired
    private PackagePersistence packagePersistence;

    public PackageService(Member member) {
        super(member);
    }

    @Override
    void add(Command command) {
        Package pakage = (Package) this.member;
        new PackageParser().addMembers(pakage, command);
        this.packagePersistence.update(pakage);
    }

    Member open(Command command) {
        String name = command.getString(CommandType.OPEN.getName());
        Member member = ((Package)this.member).find(name);
        if (member == null) {
            throw new CommandParserException(Error.MEMBER_NOT_FOUND, name);
        }
        return member;
    }

    @Override
    void accept(ServiceVisitor serviceVisitor) {
        serviceVisitor.visit(this);
    }

}
