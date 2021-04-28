package com.usantatecla.ustumlserver.domain.model;

import java.util.StringJoiner;

class UstUMLGenerator extends Generator {

    @Override
    void visit(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.merge(new StringJoiner(" ").add("class:").add(clazz.getName()));
        stringJoiner.merge(new StringJoiner(" ").add("modifiers:"));
        for (Modifier modifier : clazz.getModifiers()) {
            stringJoiner.merge(new StringJoiner(" ").add("\t-").add(this.getUML(modifier)));
        }
        stringJoiner.merge(new StringJoiner(" ").add("members:"));
        for (Attribute attribute : clazz.getAttributes()) {
            stringJoiner.merge(new StringJoiner(" ").add("\t-").add("definition:").add(this.getUML(attribute)));
        }
        for (Method method : clazz.getMethods()) {
            stringJoiner.merge(new StringJoiner(" ").add("\t-").add("definition:").add(this.getUML(method)));
        }
        this.stringJoiner.merge(stringJoiner);
    }

    @Override
    String getUML(Modifier modifier) {
        return modifier.getUSTUML();
    }

    @Override
    String getUML(String name, String type) {
        return new StringJoiner(" ").add(type).add(name).toString();
    }

}
