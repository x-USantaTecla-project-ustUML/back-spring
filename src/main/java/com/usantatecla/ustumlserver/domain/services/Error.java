package com.usantatecla.ustumlserver.domain.services;

public enum Error {

    COMMAND_NOT_FOUND("The command was not found"),
    KEY_NOT_FOUND("The json key was not found"),
    INVALID_JSON("The json value is not valid"),
    MEMBER_NOT_FOUND("The member was not found"),
    NULL;
    private String detail;

    Error(String detail) {
        this.detail = detail;
    }

    Error() {
    }

    String getDetail() {
        return this.detail;
    }

    boolean isNull() {
        return this == Error.NULL;
    }

}
