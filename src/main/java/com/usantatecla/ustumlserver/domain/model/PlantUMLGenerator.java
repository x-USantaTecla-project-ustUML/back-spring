package com.usantatecla.ustumlserver.domain.model;

import java.util.StringJoiner;

public class PlantUMLGenerator extends Generator {

    @Override
    String visit(Package pakage) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.merge(new StringJoiner(" ").add("package").add(pakage.getName()).add("{"));
        for (Member member : pakage.getMembers()) {
            stringJoiner.add("\t" + this.tabulate(member.accept(this)));
        }
        return stringJoiner.add("}").toString();
    }

    @Override
    String visit(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        StringJoiner classHeaderJoiner = new StringJoiner(" ");
        for (Modifier modifier : clazz.getModifiers()) {
            if (!modifier.isVisibility()) {
                classHeaderJoiner.add(modifier.getPlantUML());
            }
        }
        classHeaderJoiner.add("class").add(clazz.getName()).add("{");
        stringJoiner.merge(classHeaderJoiner);
        for (Attribute attribute : clazz.getAttributes()) {
            stringJoiner.add("\t" + this.getUML(attribute));
        }
        for (Method method : clazz.getMethods()) {
            stringJoiner.add("\t" + this.getUML(method));
        }
        return stringJoiner.add("}").toString();
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

    @Override
    String getUML(Method method) {
        return new StringJoiner(" ")
                .add(this.getModifiersUML(method.getModifiers()))
                .add(method.getName()).toString() +
                this.getParametersUML(method.getParameters()) + ": " +
                method.getType();
    }

}
