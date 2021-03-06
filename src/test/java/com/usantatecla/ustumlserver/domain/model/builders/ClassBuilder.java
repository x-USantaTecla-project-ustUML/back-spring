package com.usantatecla.ustumlserver.domain.model.builders;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Attribute;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Method;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassBuilder extends MemberBuilder {

    private BuilderContext context;
    private String id;
    protected String name;
    protected List<Modifier> modifiers;
    protected List<Attribute> attributes;
    private List<Method> methods;
    private AttributeBuilder attributeBuilder;
    private MethodBuilder methodBuilder;

    public ClassBuilder() {
        this.context = BuilderContext.ON_CLASS;
        this.name = "Name";
        this.modifiers = new ArrayList<>();
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    public ClassBuilder(Class clazz) {
        this.context = BuilderContext.ON_CLASS;
        this.name = clazz.getName();
        this.modifiers = clazz.getModifiers();
        this.attributes = clazz.getAttributes();
        this.methods = clazz.getMethods();
        this.relations = clazz.getRelations();
    }

    public ClassBuilder id(String id) {
        this.id = id;
        return this;
    }

    public ClassBuilder name(String name) {
        switch (this.context) {
            case ON_CLASS:
                this.name = name;
                break;
            case ON_ATTRIBUTE:
                this.attributeBuilder.name(name);
                break;
            case ON_METHOD:
                this.methodBuilder.name(name);
                break;
        }
        return this;
    }

    public ClassBuilder type(String name) {
        assert this.context != BuilderContext.ON_CLASS;

        switch (this.context) {
            case ON_ATTRIBUTE:
                this.attributeBuilder.type(name);
                break;
            case ON_METHOD:
                this.methodBuilder.type(name);
                break;
        }
        return this;
    }

    public ClassBuilder parameter() {
        assert this.context == BuilderContext.ON_METHOD;

        this.methodBuilder.parameter();
        return this;
    }

    public ClassBuilder modifiers(Modifier... modifiers) {
        this.modifiers = new ArrayList<>(Arrays.asList(modifiers));
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

    public ClassBuilder _public() {
        this.addModifier(Modifier.PUBLIC);
        return this;
    }

    public ClassBuilder _private() {
        assert this.context != BuilderContext.ON_CLASS;

        this.addModifier(Modifier.PRIVATE);
        return this;
    }

    public ClassBuilder _package() {
        this.addModifier(Modifier.PACKAGE);
        return this;
    }

    public ClassBuilder _protected() {
        this.addModifier(Modifier.PROTECTED);
        return this;
    }

    public ClassBuilder _abstract() {
        assert this.context != BuilderContext.ON_ATTRIBUTE;

        this.addModifier(Modifier.ABSTRACT);
        return this;
    }

    public ClassBuilder _final() {
        assert this.context == BuilderContext.ON_ATTRIBUTE;

        this.addModifier(Modifier.FINAL);
        return this;
    }

    public ClassBuilder _static() {
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

    @Override
    public ClassBuilder use() {
        super.use();
        return this;
    }

    @Override
    public ClassBuilder target(Member member) {
        super.target(member);
        return this;
    }

    @Override
    public ClassBuilder role(String role) {
        super.role(role);
        return this;
    }

    public Class build() {
        if (this.attributeBuilder != null) {
            this.attributes.add(this.attributeBuilder.build());
        }
        if (this.methodBuilder != null) {
            this.methods.add(this.methodBuilder.build());
        }
        Class clazz = createClass();
        clazz.setId(this.id);
        clazz.setMethods(this.methods);
        this.setRelations(clazz);
        return clazz;
    }

    public Class createClass() {
        return new Class(this.name, this.modifiers, this.attributes);
    }

}
