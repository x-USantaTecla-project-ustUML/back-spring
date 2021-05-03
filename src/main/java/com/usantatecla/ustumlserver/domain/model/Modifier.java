package com.usantatecla.ustumlserver.domain.model;

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

    String getUstUML() {
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
        for (Modifier modifier : Modifier.values()) {
            if (modifier.getUstUML().equals(string)) {
                return modifier;
            }
        }
        return Modifier.NULL;
    }

}
