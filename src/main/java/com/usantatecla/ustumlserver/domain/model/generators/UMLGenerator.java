package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.domain.model.classDiagram.*;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;

import java.util.List;
import java.util.StringJoiner;

abstract class UMLGenerator extends Generator {

    protected static final int MAX_DEPTH = 1;

    protected static final String TAB_CHAR = "  ";
    protected static final String EOL_CHAR = "\n";
    protected static final String ALLOW_MIXING = "allow_mixing\n";

    protected int depthLevel;

    public UMLGenerator() {
        this.depthLevel = 0;
    }

    abstract String getUML(Relation relation);

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
        if (modifiers.isEmpty()) {
            stringJoiner.add(this.getUML(Modifier.PACKAGE));
        } else {
            for (Modifier modifier : modifiers) {
                stringJoiner.add(this.getUML(modifier));
            }
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
        return string.replace(UMLGenerator.EOL_CHAR, UMLGenerator.EOL_CHAR + UMLGenerator.TAB_CHAR + UMLGenerator.TAB_CHAR);
    }

}
