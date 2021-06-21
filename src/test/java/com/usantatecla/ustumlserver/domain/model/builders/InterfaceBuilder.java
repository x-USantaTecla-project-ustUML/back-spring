package com.usantatecla.ustumlserver.domain.model.builders;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.*;

public class InterfaceBuilder extends ClassBuilder {

    @Override
    public InterfaceBuilder id(String id) {
        super.id(id);
        return this;
    }

    @Override
    public InterfaceBuilder name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public InterfaceBuilder type(String name) {
        super.type(name);
        return this;
    }

    @Override
    public InterfaceBuilder parameter() {
        super.parameter();
        return this;
    }

    @Override
    public InterfaceBuilder modifiers(Modifier... modifiers) {
        super.modifiers(modifiers);
        return this;
    }

    @Override
    public InterfaceBuilder attributes(Attribute... attributes) {
        super.attributes(attributes);
        return this;
    }

    @Override
    public InterfaceBuilder methods(Method... methods) {
        super.methods(methods);
        return this;
    }

    @Override
    public InterfaceBuilder _public() {
        super._public();
        return this;
    }

    @Override
    public InterfaceBuilder _private() {
        super._private();
        return this;
    }

    @Override
    public InterfaceBuilder _package() {
        super._package();
        return this;
    }

    @Override
    public InterfaceBuilder _protected() {
        super._protected();
        return this;
    }

    @Override
    public InterfaceBuilder _abstract() {
        super._abstract();
        return this;
    }

    @Override
    public InterfaceBuilder _final() {
        super._final();
        return this;
    }

    @Override
    public InterfaceBuilder _static() {
        super._static();
        return this;
    }

    @Override
    public InterfaceBuilder attribute() {
        super.attribute();
        return this;
    }

    @Override
    public InterfaceBuilder method() {
        super.method();
        return this;
    }

    @Override
    public InterfaceBuilder use() {
        super.use();
        return this;
    }

    @Override
    public InterfaceBuilder target(Member member) {
        super.target(member);
        return this;
    }

    @Override
    public InterfaceBuilder role(String role) {
        super.role(role);
        return this;
    }

    @Override
    public Interface build() {
        return (Interface) super.build();
    }

    @Override
    public Class createClass() {
        return new Interface(this.name, this.modifiers, this.attributes);
    }
}
