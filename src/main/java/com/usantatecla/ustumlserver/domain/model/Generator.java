package com.usantatecla.ustumlserver.domain.model;

import java.util.List;
import java.util.StringJoiner;

abstract class Generator {

    protected static final String TAB_CHAR = "  ";
    protected static final String EOL_CHAR = "\n";

    public String generate(Member member) {
        return member.accept(this);
    }

    abstract String visit(Account account);

    abstract String visit(Package pakage);

    abstract String visit(Class clazz);

    abstract String visit(Use use, Member origin);

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
        return string.replace(Generator.EOL_CHAR, Generator.EOL_CHAR + Generator.TAB_CHAR+ Generator.TAB_CHAR);
    }

}
