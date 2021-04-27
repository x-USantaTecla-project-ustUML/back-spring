package com.usantatecla.ustumlserver.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

class Method extends Definition {

    private List<Parameter> parameters;

    Method() {
        super();
        this.parameters = new ArrayList<>();
    }

    @Override
    void setModifiers(List<Modifier> modifiers) {
        assert !modifiers.contains(Modifier.FINAL);
        super.setModifiers(modifiers);
    }

    void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    String getUSTUML() {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (Parameter parameter : this.parameters) {
            stringJoiner.add(parameter.getUSTUML());
        }
        return super.getUSTUML() + "(" + stringJoiner.toString() + ")";
    }

    @Override
    String getPlantUML() {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (Parameter parameter : this.parameters) {
            stringJoiner.add(parameter.getPlantUML());
        }
        return super.getPlantUML() + "(" + stringJoiner.toString() + ")";
    }

}
