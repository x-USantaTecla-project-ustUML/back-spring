package com.usantatecla.ustumlserver.domain.model;

import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class Definition {

    protected String name;
    protected String type;
    protected List<Modifier> modifiers;

    public Definition(String name, String type, List<Modifier> modifiers) {
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public List<Modifier> getModifiers() {
        return this.modifiers;
    }

}
