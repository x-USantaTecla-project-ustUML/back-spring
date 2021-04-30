package com.usantatecla.ustumlserver.domain.services;

import java.util.function.Supplier;

enum CommandType {

    ADD(AddService::new);

    private Supplier<CommandParser> commandParserCreator;

    CommandType(Supplier<CommandParser> commandParserCreator) {
        this.commandParserCreator = commandParserCreator;
    }

    CommandParser create() {
        return this.commandParserCreator.get();
    }

    static CommandType get(String command) {
        return CommandType.valueOf(command);
    }

}
