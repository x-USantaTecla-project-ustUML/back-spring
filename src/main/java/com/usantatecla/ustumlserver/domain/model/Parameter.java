package com.usantatecla.ustumlserver.domain.model;

import java.util.StringJoiner;

class Parameter {

    private String name;
    private String type;

    Parameter(String name, String type) {
        this.name = name;
        this.type = type;
    }

    String getUSTUML() {
        return new StringJoiner(" ").add(this.type).add(this.name).toString();
    }

    String getPlantUML() {
        return new StringJoiner(" ").add(this.type).add(this.name).toString();
    }

}
