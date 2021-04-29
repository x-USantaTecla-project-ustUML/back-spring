package com.usantatecla.ustumlserver.domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MethodBuilder extends DefinitionBuilder {

    private List<Parameter> parameters;

    MethodBuilder() {
        super();
        this.parameters = new ArrayList<>();
    }

    @Override
    MethodBuilder name(String name) {
        super.name(name);
        return this;
    }

    @Override
    MethodBuilder type(String type) {
        super.type(type);
        return this;
    }

    @Override
    MethodBuilder modifiers(Modifier... modifiers) {
        super.modifiers(modifiers);
        return this;
    }

    MethodBuilder parameters(Parameter... parameters) {
        this.parameters = Arrays.asList(parameters);
        return this;
    }

    @Override
    Method build() {
        Method method = new Method(this.name, this.type, this.modifiers);
        method.setParameters(parameters);
        return method;
    }


}
