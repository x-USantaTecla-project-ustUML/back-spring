package com.usantatecla.ustumlserver.domain.model;

import java.util.StringJoiner;

public class UstUMLGenerator extends Generator {

    @Override
    String visit(Package pakage) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.merge(new StringJoiner(" ").add("package:").add(pakage.getName()));
        stringJoiner.add("members:");
        for (Member member : pakage.getMembers()) {
            stringJoiner.add("\t- " + this.tabulate(member.accept(this)));
        }
        return stringJoiner.toString();
    }

    @Override
    String visit(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.merge(new StringJoiner(" ").add("class:").add(clazz.getName()));
        stringJoiner.merge(new StringJoiner(" ").add("modifiers:").add(this.getModifiersUML(clazz.getModifiers())));
        stringJoiner.add("members:");
        for (Attribute attribute : clazz.getAttributes()) {
            stringJoiner.merge(new StringJoiner(" ").add("\t-").add("member:").add(this.getUML(attribute)));
        }
        for (Method method : clazz.getMethods()) {
            stringJoiner.merge(new StringJoiner(" ").add("\t-").add("member:").add(this.getUML(method)));
        }
        return stringJoiner.toString();
    }

    @Override
    String getUML(Modifier modifier) {
        return modifier.getUstUML();
    }

    @Override
    String getUML(String name, String type) {
        return new StringJoiner(" ").add(type).add(name).toString();
    }

    @Override
    String getUML(Method method) {
        return new StringJoiner(" ")
                .add(this.getModifiersUML(method.getModifiers()))
                .add(method.getType()).add(method.getName()).toString() +
                this.getParametersUML(method.getParameters());
    }

}
