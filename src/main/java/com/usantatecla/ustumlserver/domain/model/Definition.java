package com.usantatecla.ustumlserver.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

abstract class Definition {

    protected String name;
    protected String type;
    protected List<Modifier> modifiers;

    Definition() {
        this.modifiers = new ArrayList<>();
    }

    void setName(String name) {
        this.name = name;
    }

    void setType(String type) {
        this.type = type;
    }

    void setModifiers(List<Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    String getUSTUML() {
        assert this.name != null;
        assert this.type != null;
        assert !this.modifiers.isEmpty();

        StringJoiner stringJoiner = new StringJoiner(" ");
        for (Modifier modifier : this.modifiers) {
            stringJoiner.add(modifier.getUSTUML());
        }
        return this.addTypeName(stringJoiner).toString();
    }

    private StringJoiner addTypeName(StringJoiner stringJoiner) {
        stringJoiner.add(this.type).add(this.name);
        return stringJoiner;
    }

    String getPlantUML() {
        assert this.name != null;
        assert this.type != null;
        assert !this.modifiers.isEmpty();

        StringJoiner stringJoiner = new StringJoiner(" ");
        for (Modifier modifier : this.modifiers) {
            stringJoiner.add(modifier.getPlantUML());
        }
        return this.addTypeName(stringJoiner).toString();
    }

}
