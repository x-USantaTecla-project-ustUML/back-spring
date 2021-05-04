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
        assert !this.isNull();

        return this.commandParserCreator.get();
    }

    boolean isNull() {
        return CommandType.NULL == this;
    }

    static CommandType get(String command) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.getName().equals(command)) {
                return commandType;
            }
        }
        return CommandType.NULL;
    }

    public String getName() {
        return this.name().toLowerCase();
    }
}
