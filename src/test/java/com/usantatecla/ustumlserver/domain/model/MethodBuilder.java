package com.usantatecla.ustumlserver.domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodBuilder extends DefinitionBuilder {

    private List<Parameter> parameters;

    public MethodBuilder() {
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
    public MethodBuilder modifiers(Modifier... modifiers) {
        super.modifiers(modifiers);
        return this;
    }

    public MethodBuilder parameters(Parameter... parameters) {
        this.parameters = new ArrayList<>(Arrays.asList(parameters));
        return this;
    }

    public MethodBuilder parameter() {
        this.parameters.add(new Parameter("name", "Type"));
        return this;
    }

    @Override
    public Method build() {
        Method method = new Method(this.name, this.type, this.modifiers);
        method.setParameters(parameters);
        return method;
    }


}
