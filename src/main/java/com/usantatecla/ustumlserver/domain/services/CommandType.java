package com.usantatecla.ustumlserver.domain.services;

import java.util.function.Supplier;

enum CommandType {

    ADD(AddService::new),
    NULL;

    private Supplier<CommandParser> commandParserCreator;

    CommandType(Supplier<CommandParser> commandParserCreator) {
        this.commandParserCreator = commandParserCreator;
    }

    CommandType() {
    }

    CommandParser create() {
        assert this.commandParserCreator != null;

        return this.commandParserCreator.get();
    }

    static CommandType get(String command) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.name().toLowerCase().equals(command)) {
                return commandType;
            }
        }
        return CommandType.NULL;
    }

}
