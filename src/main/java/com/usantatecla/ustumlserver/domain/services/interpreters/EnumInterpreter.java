package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.domain.services.parsers.classDiagram.EnumObjectParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;

public class EnumInterpreter extends ClassInterpreter {

    public EnumInterpreter(Account account, Member member) {
        super(account, member);
    }

    @Override
    public void addCommandSections(Command command) {
        super.addCommandSections(command);
        for (Command objectCommand : command.getCommands(Command.OBJECTS)) {
            ((Enum) this.member).addObject(new EnumObjectParser().get(objectCommand));
        }
    }

    @Override
    public boolean isInvalidAddKeys(Command command) {
        return super.isInvalidAddKeys(command) && !command.has(Command.OBJECTS);
    }

    @Override
    public void deleteCommandSections(Command command) {
        super.deleteCommandSections(command);
        for (Command objectCommand : command.getCommands(Command.OBJECTS)) {
            ((Enum) this.member).deleteObject(new EnumObjectParser().get(objectCommand));
        }
    }

    @Override
    public boolean isInvalidDeleteKeys(Command command) {
        return super.isInvalidDeleteKeys(command) && !command.has(Command.OBJECTS);
    }

    @Override
    public void modifyCommandSections(Command command) {
        super.modifyCommandSections(command);
        for (Command objectCommand : command.getCommands(Command.OBJECTS)) {
            ((Enum) this.member).modifyObject(new EnumObjectParser().get(objectCommand), objectCommand.getString(Command.SET));
        }
    }

    @Override
    public boolean isInvalidModifyKeys(Command command) {
        return super.isInvalidModifyKeys(command) && !command.has(Command.OBJECTS);
    }

}
