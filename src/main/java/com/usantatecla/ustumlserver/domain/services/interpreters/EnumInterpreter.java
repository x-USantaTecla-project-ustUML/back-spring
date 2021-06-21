package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.domain.services.parsers.EnumObjectParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;

public class EnumInterpreter extends ClassInterpreter {

    public EnumInterpreter(Account account, Member member) {
        super(account, member);
    }

    @Override
    protected void addCommandSections(Command command) {
        super.addCommandSections(command);
        for (Command objectCommand : command.getCommands(Command.OBJECTS)) {
            ((Enum) this.member).addObject(new EnumObjectParser().get(objectCommand));
        }
    }

    @Override
    protected boolean isInvalidAddKeys(Command command) {
        return super.isInvalidAddKeys(command) && !command.has(Command.OBJECTS);
    }

    @Override
    protected void modifyCommandSections(Command command) {
        super.modifyCommandSections(command);
        for (Command objectCommand : command.getCommands(Command.OBJECTS)) {
            ((Enum) this.member).modifyObject(new EnumObjectParser().get(objectCommand), objectCommand.getString(Command.SET));
        }
    }

    @Override
    protected boolean isInvalidModifyKeys(Command command) {
        return super.isInvalidModifyKeys(command) && !command.has(Command.OBJECTS);
    }

}
