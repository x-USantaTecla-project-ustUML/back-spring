package com.usantatecla.ustumlserver.domain.model;

import java.util.List;

abstract class Definition {

    protected String name;
    protected String type;
    protected List<Modifier> modifiers;

    Definition(String name, String type, List<Modifier> modifiers) {
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
    }

    String getName() {
        return this.name;
    }

    String getType() {
        return this.type;
    }

    List<Modifier> getModifiers() {
        return this.modifiers;
    }
}
