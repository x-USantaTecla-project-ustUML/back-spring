package com.usantatecla.ustumlserver.domain.model;

public enum Role {
    AUTHENTICATED;

    public static final String PREFIX = "ROLE_";

    public String withPrefix() {
        return PREFIX + this;
    }

}
