package com.usantatecla.ustumlserver.domain.model;

import java.util.StringJoiner;

public class PlantUMLGenerator extends Generator {

    @Override
    void visit(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (Modifier modifier : clazz.getModifiers()) {
            if (modifier == Modifier.ABSTRACT) {
                stringJoiner.add(modifier.getPlantUML());
            }
        }
        stringJoiner.add("class").add(clazz.getName()).add("{");
        this.stringJoiner.merge(stringJoiner);
        for (Attribute attribute : clazz.getAttributes()) {
            this.stringJoiner.add(this.getUML(attribute));
        }
        for (Method method : clazz.getMethods()) {
            this.stringJoiner.add(this.getUML(method));
        }
        this.stringJoiner.add("}");
    }

    @Override
    String getUML(Modifier modifier) {
        if (!modifier.isVisibility()) {
            return "{" + modifier.getPlantUML() + "}";
        }
        return modifier.getPlantUML();
    }

    @Override
    String getUML(String name, String type) {
        return new StringJoiner(" ").add(name + ":").add(type).toString();
    }

}
