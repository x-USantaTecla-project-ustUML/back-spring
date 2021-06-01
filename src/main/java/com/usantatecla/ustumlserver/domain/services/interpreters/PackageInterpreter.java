package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.domain.services.parsers.PackageParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandType;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class PackageInterpreter extends MemberInterpreter {

    @Autowired
    private PackagePersistence packagePersistence;

    public PackageInterpreter(Member member) {
        super(member);
    }

    @Override
    public void add(Command command) {
        Package pakage = (Package) this.member;
        new PackageParser().addMembers(pakage, command);
        new PackageParser().addRelation(pakage, command, this.accountPersistence);
        this.packagePersistence.update(pakage);
    }

    public Member open(Command command) {
        String name = command.getString(CommandType.OPEN.getName());
        Member member = ((Package)this.member).find(name);
        if (member == null) {
            throw new ServiceException(ErrorMessage.MEMBER_NOT_FOUND, name);
        }
        return member;
    }

    @Override
    public void accept(InterpreterVisitor interpreterVisitor) {
        interpreterVisitor.visit(this);
    }

}
