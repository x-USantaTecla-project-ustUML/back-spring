package com.usantatecla.ustumlserver.domain.model;

import java.util.StringJoiner;

public class UstUMLGenerator extends Generator {

    @Override
    void visit(Package pakage) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.merge(new StringJoiner(" ").add("package:").add(pakage.getName()));
        this.stringJoiner.add("members");
        for(Member member : pakage.getMembers()) {
            member.accept(this);
        }
    }

    @Override
    void visit(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.merge(new StringJoiner(" ").add("class:").add(clazz.getName()));
        StringJoiner stringJoinerModifier = new StringJoiner(" ").add("modifiers:");
        for (Modifier modifier : clazz.getModifiers()) {
            stringJoinerModifier.add(this.getUML(modifier));
        }
        stringJoiner.merge(stringJoinerModifier);
        stringJoiner.merge(new StringJoiner(" ").add("members:"));
        for (Attribute attribute : clazz.getAttributes()) {
            stringJoiner.merge(new StringJoiner(" ").add("\t-").add("member:").add(this.getUML(attribute)));
        }
        for (Method method : clazz.getMethods()) {
            stringJoiner.merge(new StringJoiner(" ").add("\t-").add("member:").add(this.getUML(method)));
        }
        this.stringJoiner.merge(stringJoiner);
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
                .add(method.getType()).add(method.getName() + "(").toString() +
                this.getParametersUML(method.getParameters()) + ")";
    }

}
