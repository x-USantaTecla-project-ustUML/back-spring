package com.usantatecla.ustumlserver.domain.model;

public enum Modifier {

    PUBLIC("public", "+"),
    PACKAGE("package", "~"),
    PRIVATE("private", "-"),
    PROTECTED("protected", "#"),
    FINAL("final", "final"),
    STATIC("static", "static"),
    ABSTRACT("abstract", "abstract");

    private String ustUML;
    private String plantUML;

    Modifier(String ustUML, String plantUML) {
        this.ustUML = ustUML;
        this.plantUML = plantUML;
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

}
