package com.usantatecla.ustumlserver.domain.model;

import java.util.StringJoiner;

abstract class Generator {

    protected StringJoiner stringJoiner = new StringJoiner("\n");

    //TODO nombre de atributo class?
    abstract void visit(Class clazz);

    String getUML(Attribute attribute) {
        return this.getUML((Definition) attribute);
    }

    private String getUML(Definition definition) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (Modifier modifier : definition.getModifiers()) {
            stringJoiner.add(this.getUML(modifier));
        }
        return stringJoiner.add(this.getUML(definition.getName(), definition.getType())).toString();
    }

    abstract String getUML(Modifier modifier);

    abstract String getUML(String name, String type);

    String getUML(Method method) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (Parameter parameter : method.getParameters()) {
            stringJoiner.add(this.getUML(parameter));
        }
        return this.getUML((Definition) method) + "(" + stringJoiner.toString() + ")";
    }

    private String getUML(Parameter parameter) {
        return this.getUML(parameter.getName(), parameter.getType());
    }

    @Override
    public String toString() {
        return this.stringJoiner.toString();
    }
}
