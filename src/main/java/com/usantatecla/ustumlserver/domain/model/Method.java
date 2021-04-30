package com.usantatecla.ustumlserver.domain.model;

import java.util.List;

public class Method extends Definition {

    private List<Parameter> parameters;

    Method(String name, String type, List<Modifier> modifiers) {
        super(name, type, modifiers);
        assert !modifiers.contains(Modifier.FINAL);
    }

    List<Parameter> getParameters() {
        return this.parameters;
    }

    void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

}
