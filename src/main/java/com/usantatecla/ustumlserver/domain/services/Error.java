package com.usantatecla.ustumlserver.domain.services;

public enum Error {

    COMMAND_NOT_FOUND("The command %s does not exists"),
    KEY_NOT_FOUND("%s is required"),
    INVALID_VALUE("The value of %s is not valid"),
    INVALID_ARRAY_VALUE("The value of a member is not valid"),
    MEMBER_TYPE_NOT_FOUND("The member type of %s does not exists"),
    INVALID_NAME("%s is not a valid name"),
    INVALID_CLASS_MODIFIERS("%s is not valid modifiers"),
    INVALID_CLASS_MEMBER("%s is not a valid member"),
    MEMBER_ALREADY_EXISTS("The member %s already exists in this package"),
    MEMBER_NOT_FOUND("The member %s does not exists in this package");

    private String detail;

    Error(String detail) {
        this.detail = detail;
    }

    String getDetail() {
        return this.detail;
    }

}
