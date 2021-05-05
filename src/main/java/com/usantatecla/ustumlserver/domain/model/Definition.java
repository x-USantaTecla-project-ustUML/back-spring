package com.usantatecla.ustumlserver.domain.model;

import java.util.List;

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

    List<Modifier> getModifiers() {
        return this.modifiers;
    }

}
