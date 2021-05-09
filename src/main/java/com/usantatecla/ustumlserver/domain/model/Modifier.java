package com.usantatecla.ustumlserver.domain.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum Modifier {

    PUBLIC("public", "+"),
    PACKAGE("package", "~"),
    PRIVATE("private", "-"),
    PROTECTED("protected", "#"),
    FINAL("final", "final"),
    STATIC("static", "static"),
    ABSTRACT("abstract", "abstract"),
    NULL;

    private String ustUML;
    private String plantUML;

    Modifier(String ustUML, String plantUML) {
        this.ustUML = ustUML;
        this.plantUML = plantUML;
    }

    Modifier() {
    }

    public String getUstUML() {
        return this.ustUML;
    }

    String getPlantUML() {
        return this.plantUML;
    }

    boolean isVisibility() {
        return this == Modifier.PUBLIC || this == Modifier.PRIVATE ||
                this == Modifier.PACKAGE || this == Modifier.PROTECTED;
    }

    public boolean isNull() {
        return this == Modifier.NULL;
    }

    public static Modifier get(String string) {
        for (Modifier modifier : Modifier.getValues()) {
            if (modifier.getUstUML().equals(string)) {
                return modifier;
            }
        }
        return Modifier.NULL;
    }

    private static List<Modifier> getValues() {
        List<Modifier> modifiers = new LinkedList<>(Arrays.asList(Modifier.values()));
        modifiers.remove(Modifier.NULL);
        return modifiers;
    }

    static boolean isThereVisibility(List<Modifier> modifiers) {
        assert modifiers != null;

        for (Modifier modifier : modifiers) {
            if (modifier.isVisibility()) {
                return true;
            }
        }
        return false;
    }

    public static String getNotAmongRegex() {
        StringBuilder regex = new StringBuilder().append("(?!(");
        List<Modifier> modifiers = Modifier.getValues();
        for (Modifier modifier : modifiers) {
            regex.append(modifier.getUstUML());
            if (modifier != modifiers.get(modifiers.size() - 1)) {
                regex.append("|");
            }
        }
        return regex.append("))").toString();
    }

}
