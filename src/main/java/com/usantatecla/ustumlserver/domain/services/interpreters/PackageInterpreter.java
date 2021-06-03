package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.PackageParser;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class PackageInterpreter extends WithMembersInterpreter {

    @Autowired
    private PackagePersistence packagePersistence;

    public PackageInterpreter(Member member) {
        super(member);
    }

    @Override
    public void add(Command command) {
        Package pakage = (Package) this.member;
        if (command.has(Command.MEMBERS)) {
            new PackageParser().addMembers(pakage, command);
        }
        if (command.has(Command.RELATIONS)) {
            new PackageParser().addRelation(pakage, command, this.accountPersistence);
        }
        if (!command.has(Command.MEMBERS) && !command.has(Command.RELATIONS)) {
            throw new ParserException(ErrorMessage.KEY_NOT_FOUND, "Members or relations");
        }
        this.packagePersistence.update(pakage);
    }

    @Override
    Member find(String name) {
        return ((Package) this.member).find(name);
    }

}
