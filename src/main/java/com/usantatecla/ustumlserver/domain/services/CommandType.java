package com.usantatecla.ustumlserver.domain.services;

enum CommandType {

    ADD,
    NULL;

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
