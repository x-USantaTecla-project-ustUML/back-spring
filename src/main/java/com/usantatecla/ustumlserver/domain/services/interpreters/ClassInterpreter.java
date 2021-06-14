package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.ClassPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import org.springframework.beans.factory.annotation.Autowired;

public class ClassInterpreter extends MemberInterpreter {

    @Autowired
    private ClassPersistence classPersistence;

    public ClassInterpreter(Account account, Member member) {
        super(account, member);
    }

    @Override
    public void add(Command command) { //TODO
        super.add(command);
        Class clazz = (Class) this.member;
        this.addRelations(command);
        this.member = this.classPersistence.update(clazz);
    }

    @Override
    public void modify(Command command) {
        super.modify(command);
        Class clazz = (Class) this.member;
        this.modifyRelations(command);
        this.member = this.classPersistence.update(clazz);
    }

}
