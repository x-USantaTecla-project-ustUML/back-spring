package com.usantatecla.ustumlserver.domain.model;

public abstract class Member {

    protected String name;

    Member(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    abstract void accept(Generator generator);
}
