package com.usantatecla.ustumlserver.domain.model;

import java.util.ArrayList;
import java.util.List;

class Class extends Member {

    private List<Modifier> modifiers;
    private List<Attribute> attributes;
    private List<Method> methods;

    public Class(String name, List<Modifier> modifiers, List<Attribute> attributes) {
        super(name);
        this.modifiers = modifiers;
        this.attributes = attributes;
        this.methods = new ArrayList<>();
    }

    List<Modifier> getModifiers() {
        return this.modifiers;
    }

    List<Attribute> getAttributes() {
        return this.attributes;
    }

    List<Method> getMethods() {
        return this.methods;
    }

    void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    @Override
    public void accept(Generator generator) {
        generator.visit(this);
    }

}
