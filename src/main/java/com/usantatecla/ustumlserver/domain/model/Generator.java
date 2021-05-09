package com.usantatecla.ustumlserver.domain.model;

import java.util.List;
import java.util.StringJoiner;

abstract class Generator {

    abstract String visit(Package pakage);

    abstract String visit(Class clazz);

    protected String getUML(Attribute attribute) {
        return new StringJoiner(" ")
                .add(this.getModifiersUML(attribute.getModifiers()))
                .add(this.getUML(attribute.getName(), attribute.getType())).toString();
    }

    abstract String getUML(Modifier modifier);

    abstract String getUML(String name, String type);

    abstract String getUML(Method method);

    protected String getModifiersUML(List<Modifier> modifiers) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (Modifier modifier : modifiers) {
            stringJoiner.add(this.getUML(modifier));
        }
        return stringJoiner.toString();
    }

    protected String getParametersUML(List<Parameter> parameters) {
        StringJoiner stringJoiner = new StringJoiner(", ", "(", ")");
        for (Parameter parameter : parameters) {
            stringJoiner.add(this.getUML(parameter));
        }
        return stringJoiner.toString();
    }

    protected String getUML(Parameter parameter) {
        return this.getUML(parameter.getName(), parameter.getType());
    }

    protected CharSequence tabulate(String string) {
        return string.replace("\n", "\n\t");
    }

}
