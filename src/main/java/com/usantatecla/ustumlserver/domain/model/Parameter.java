package com.usantatecla.ustumlserver.domain.model;

public class Parameter {

    private String name;
    private String type;

    public Parameter(String name, String type) {
        this.name = name;
        this.type = type;
    }

    String getName() {
        return this.name;
    }

    String getType() {
        return this.type;
    }

}
