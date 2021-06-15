package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.ClassPersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.*;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class ClassInterpreter extends MemberInterpreter {

    @Autowired
    private ClassPersistence classPersistence;

    public ClassInterpreter(Account account, Member member) {
        super(account, member);
    }

    @Override
    public void add(Command command) {
        Class clazz = (Class) this.member;
        if (command.has(ClassParser.MODIFIERS_KEY)) {
            throw new ParserException(ErrorMessage.MEMBER_NOT_ALLOWED, "modifiers");
        }
        if (command.has(ClassParser.MEMBERS_KEY)) {
            ClassMemberParser classMemberParser = new ClassMemberParser();
            classMemberParser.get(command);
            clazz.addAttributes(classMemberParser.getAttributes());
            clazz.addMethods(classMemberParser.getMethods());
        }
        this.addRelations(command);
        this.member = this.classPersistence.update(clazz);
    }

    @Override
    public void modify(Command command) {
        Class clazz = (Class) this.member;
        if (command.has(ClassParser.MODIFIERS_KEY)) {
            if (!command.has(ClassParser.SET_KEY)) {
                throw new ParserException(ErrorMessage.KEY_NOT_FOUND, ClassParser.SET_KEY);
            }
            ModifierParser modifierParser = new ModifierParser();
            clazz.setModifiers(modifierParser.get(command.getString(MemberParser.SET_KEY)));
        }
        if (command.has(ClassParser.MEMBERS_KEY)) {
            ClassMemberParser classMemberParser = new ClassMemberParser();
            classMemberParser.modify(command);
            clazz.modifyAttributes(classMemberParser.getAttributes());
            clazz.modifyMethods(classMemberParser.getMethods());
        }
        this.modifyRelations(command);
        this.member = this.classPersistence.update(clazz);

    }

}
