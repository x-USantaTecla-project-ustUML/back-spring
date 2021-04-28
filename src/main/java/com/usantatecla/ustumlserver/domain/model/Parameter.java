package com.usantatecla.ustumlserver.domain.model;

class Parameter {

    private String name;
    private String type;

    Parameter(String name, String type) {
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
