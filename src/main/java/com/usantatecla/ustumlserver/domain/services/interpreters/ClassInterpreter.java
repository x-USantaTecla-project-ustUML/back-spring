package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.ClassPersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.ClassParser;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class ClassInterpreter extends MemberInterpreter {

    @Autowired
    private ClassPersistence classPersistence;

    public ClassInterpreter(Member member) {
        super(member);
    }

    @Override
    public void add(Command command) {
        Class clazz = (Class) this.member;
        if (command.has(Command.RELATIONS)) {
            new ClassParser().addRelation(clazz, command, this.accountPersistence);
        }
        if (!command.has(Command.MEMBERS) && !command.has(Command.RELATIONS)) {
            throw new ParserException(ErrorMessage.KEY_NOT_FOUND, "Members or relations");
        }
        this.classPersistence.update(clazz);
    }

}
