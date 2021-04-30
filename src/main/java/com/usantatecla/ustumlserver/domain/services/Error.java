package com.usantatecla.ustumlserver.domain.services;

public enum Error {

    COMMAND_NOT_FOUND,
    INVALID_JSON,
    NULL;

    boolean isNull() {
        return this == Error.NULL;
    }

}
