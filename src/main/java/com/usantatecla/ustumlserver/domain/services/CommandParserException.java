package com.usantatecla.ustumlserver.domain.services;

public class CommandParserException extends RuntimeException {
    private static final String DESCRIPTION = "Command exception";

    public CommandParserException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
