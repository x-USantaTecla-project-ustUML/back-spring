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

    public ClassBuilder attributes(Attribute... attributes) {
        this.attributes = new ArrayList<>(Arrays.asList(attributes));
        return this;
    }

    public ClassBuilder methods(Method... methods) {
        this.methods = new ArrayList<>(Arrays.asList(methods));
        return this;
    }

    public ClassBuilder publik() {
        this.addModifier(Modifier.PUBLIC);
        return this;
    }

    public ClassBuilder privat() {
        assert this.context != BuilderContext.ON_CLASS;

        this.addModifier(Modifier.PRIVATE);
        return this;
    }

    public ClassBuilder pakage() {
        this.addModifier(Modifier.PACKAGE);
        return this;
    }

    public ClassBuilder proteted() {
        assert this.context == BuilderContext.ON_ATTRIBUTE;

        this.addModifier(Modifier.PROTECTED);
        return this;
    }

    public ClassBuilder abstrat() {
        assert this.context != BuilderContext.ON_ATTRIBUTE;

        this.addModifier(Modifier.ABSTRACT);
        return this;
    }

    public ClassBuilder fainal() {
        assert this.context == BuilderContext.ON_ATTRIBUTE;

        this.addModifier(Modifier.FINAL);
        return this;
    }

    public ClassBuilder estatic() {
        assert this.context != BuilderContext.ON_CLASS;

        this.addModifier(Modifier.STATIC);
        return this;
    }

    private void addModifier(Modifier modifier) {
        switch (this.context) {
            case ON_CLASS:
                this.modifiers.add(modifier);
                break;
            case ON_ATTRIBUTE:
                this.attributeBuilder.modifiers(modifier);
                break;
            case ON_METHOD:
                this.methodBuilder.modifiers(modifier);
                break;
        }
    }

    public ClassBuilder attribute() {
        if (this.context == BuilderContext.ON_ATTRIBUTE) {
            this.attributes.add(this.attributeBuilder.build());
        } else this.context = BuilderContext.ON_ATTRIBUTE;
        this.attributeBuilder = new AttributeBuilder();
        return this;
    }

    public ClassBuilder method() {
        if (this.context == BuilderContext.ON_METHOD) {
            this.methods.add(this.methodBuilder.build());
        } else this.context = BuilderContext.ON_METHOD;
        this.methodBuilder = new MethodBuilder();
        return this;
    }

    public Class build() {
        Class clazz = new Class(this.name, this.modifiers, this.attributes);
        clazz.setMethods(this.methods);
        return clazz;
    }

}
