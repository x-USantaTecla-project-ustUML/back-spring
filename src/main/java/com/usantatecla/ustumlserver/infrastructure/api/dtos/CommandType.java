package com.usantatecla.ustumlserver.infrastructure.api.dtos;

public enum CommandType {

    ADD,
    MODIFY,
    DELETE,
    IMPORT,
    OPEN,
    CLOSE,
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
