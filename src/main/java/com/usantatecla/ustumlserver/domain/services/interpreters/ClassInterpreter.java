package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.ClassPersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.ClassParser;
import com.usantatecla.ustumlserver.domain.services.parsers.ModifierParser;
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
        Class clazz = (Class) this.member;
        if (command.has(ClassParser.MODIFIERS_KEY)) {
            ModifierParser modifierParser = new ModifierParser();
            clazz.addModifiers(modifierParser.get(command));
        }
        if (command.has(ClassParser.MEMBERS_KEY)) {

        }
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
