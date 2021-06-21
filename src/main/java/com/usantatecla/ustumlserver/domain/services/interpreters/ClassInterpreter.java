package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.persistence.ClassPersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.ClassMemberParser;
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
    public void add(Command command) {
        super.add(command);
        this.addCommandSections(command);
        this.member = this.classPersistence.update((Class) this.member);
    }

    protected void addCommandSections(Command command) {
        Class clazz = (Class) this.member;
        if (command.has(Command.MEMBERS)) {
            ClassMemberParser classMemberParser = new ClassMemberParser();
            classMemberParser.parse(command);
            clazz.addAttributes(classMemberParser.getAttributes());
            clazz.addMethods(classMemberParser.getMethods());
        }
        this.addRelations(command);
    }

    @Override
    public void modify(Command command) {
        super.modify(command);
        this.modifyCommandSections(command);
        this.member = this.classPersistence.update((Class) this.member);
    }

    protected void modifyCommandSections(Command command) {
        Class clazz = (Class) this.member;
        if (command.has(Command.MODIFIERS)) {
            clazz.setModifiers(new ModifierParser().get(command.getString(Command.SET)));
        }
        if (command.has(Command.MEMBERS)) {
            ClassMemberParser oldMembersParser = new ClassMemberParser();
            oldMembersParser.parse(command);
            ClassMemberParser newMembersParser = new ClassMemberParser();
            newMembersParser.parseModify(command);
            clazz.modifyAttributes(oldMembersParser.getAttributes(), newMembersParser.getAttributes());
            clazz.modifyMethods(oldMembersParser.getMethods(), newMembersParser.getMethods());
        }
        this.modifyRelations(command);
    }

    @Override
    public void delete(Command command) {
        super.delete(command);
        Class _class = (Class) this.member; // TODO ¿Posible problema con Interface & Enum?
        if (command.has(Command.MEMBERS)) {
            ClassMemberParser membersToDeleteParser = new ClassMemberParser();
            membersToDeleteParser.parse(command);
            _class.deleteAttributes(membersToDeleteParser.getAttributes());
            _class.deleteMethods(membersToDeleteParser.getMethods());
        }
        this.member = this.classPersistence.deleteRelations(this.member, this.deleteRelations(command));

    }

    @Override
    protected boolean isInvalidModifyKeys(Command command) {
        return super.isInvalidModifyKeys(command) && !command.has(Command.MODIFIERS);
    }
}
