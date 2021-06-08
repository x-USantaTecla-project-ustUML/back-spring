package com.usantatecla.ustumlserver.domain.model.builders;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Enum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnumBuilder extends ClassBuilder {

    private List<String> objects;

    public EnumBuilder() {
        super();
        this.objects = new ArrayList<>();
    }

    @Override
    public EnumBuilder id(String id) {
        super.id(id);
        return this;
    }

    @Override
    public EnumBuilder name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public EnumBuilder type(String name) {
        super.type(name);
        return this;
    }

    @Override
    public EnumBuilder parameter() {
        super.parameter();
        return this;
    }

    @Override
    public EnumBuilder modifiers(Modifier... modifiers) {
        super.modifiers(modifiers);
        return this;
    }

    @Override
    public EnumBuilder attributes(Attribute... attributes) {
        super.attributes(attributes);
        return this;
    }

    @Override
    public EnumBuilder methods(Method... methods) {
        super.methods(methods);
        return this;
    }

    @Override
    public EnumBuilder _public() {
        super._public();
        return this;
    }

    @Override
    public EnumBuilder _private() {
        super._private();
        return this;
    }

    @Override
    public EnumBuilder _package() {
        super._package();
        return this;
    }

    @Override
    public EnumBuilder _protected() {
        super._protected();
        return this;
    }

    @Override
    public EnumBuilder _abstract() {
        super._abstract();
        return this;
    }

    @Override
    public EnumBuilder _final() {
        super._final();
        return this;
    }

    @Override
    public EnumBuilder _static() {
        super._static();
        return this;
    }

    @Override
    public EnumBuilder attribute() {
        super.attribute();
        return this;
    }

    @Override
    public EnumBuilder method() {
        super.method();
        return this;
    }

    @Override
    public EnumBuilder use() {
        super.use();
        return this;
    }

    @Override
    public EnumBuilder target(Member member) {
        super.target(member);
        return this;
    }

    @Override
    public EnumBuilder role(String role) {
        super.role(role);
        return this;
    }

    public EnumBuilder objects(String... objects) {
        this.objects.addAll(Arrays.asList(objects));
        return this;
    }

    @Override
    public Enum build() {
        Enum _enum = (Enum) super.build();
        _enum.setObjects(this.objects);
        return _enum;
    }

    @Override
    public Class createClass() {
        return new Enum(this.name, this.modifiers, this.attributes);
    }
}
