package com.usantatecla.ustumlserver.domain.model;

abstract class Member {

    protected String name;

    Member(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    abstract void accept(Generator generator);
}
