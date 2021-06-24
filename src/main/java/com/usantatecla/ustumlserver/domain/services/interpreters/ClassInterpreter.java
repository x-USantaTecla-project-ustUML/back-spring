package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.services.parsers.classDiagram.ClassMemberParser;
import com.usantatecla.ustumlserver.domain.services.parsers.classDiagram.ModifierParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;

public class ClassInterpreter extends MemberInterpreter {

    public ClassInterpreter(Account account, Member member) {
        super(account, member);
    }

    @Override
    protected void addCommandSections(Command command) {
        super.addCommandSections(command);
        Class clazz = (Class) this.member;
        if (command.has(Command.MEMBERS)) {
            ClassMemberParser classMemberParser = new ClassMemberParser();
            classMemberParser.parse(command);
            clazz.addAttributes(classMemberParser.getAttributes());
            clazz.addMethods(classMemberParser.getMethods());
        }
    }

    @Override
    protected boolean isInvalidAddKeys(Command command) {
        return super.isInvalidModifyKeys(command) && !command.has(Command.MEMBERS);
    }

    @Override
    protected void deleteCommandSections(Command command) {
        super.deleteCommandSections(command);
        Class _class = (Class) this.member;
        if (command.has(Command.MEMBERS)) {
            ClassMemberParser membersToDeleteParser = new ClassMemberParser();
            membersToDeleteParser.parse(command);
            _class.deleteAttributes(membersToDeleteParser.getAttributes());
            _class.deleteMethods(membersToDeleteParser.getMethods());
        }
    }

    @Override
    protected boolean isInvalidDeleteKeys(Command command) {
        return super.isInvalidModifyKeys(command) && !command.has(Command.MEMBERS);
    }

    @Override
    protected void modifyCommandSections(Command command) {
        super.modifyCommandSections(command);
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
    }

    @Override
    protected boolean isInvalidModifyKeys(Command command) {
        return super.isInvalidModifyKeys(command) && !command.has(Command.MEMBERS) && !command.has(Command.MODIFIERS);
    }

}
