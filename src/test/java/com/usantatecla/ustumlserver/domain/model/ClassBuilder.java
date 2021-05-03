package com.usantatecla.ustumlserver.domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassBuilder {

    private String name;
    private List<Modifier> modifiers;
    private List<Attribute> attributes;
    private List<Method> methods;

    public ClassBuilder() {
        this.name = "Name";
        this.modifiers = new ArrayList<>();
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    public ClassBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ClassBuilder modifiers(Modifier... modifiers) {
        this.modifiers = Arrays.asList(modifiers);
        return this;
    }

    public ClassBuilder attributes(Attribute... attributes) {
        this.attributes = Arrays.asList(attributes);
        return this;
    }

    public ClassBuilder methods(Method... methods) {
        this.methods = Arrays.asList(methods);
        return this;
    }

    public Class build() {
        Class clazz = new Class(this.name, this.modifiers, this.attributes);
        clazz.setMethods(this.methods);
        return clazz;
    }

}
