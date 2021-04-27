package com.usantatecla.ustumlserver.domain.model;

public enum Modifier {

    PUBLIC("public", "+"),
    PACKAGE("package", "~"),
    PRIVATE("private", "-"),
    PROTECTED("protected", "#"),
    FINAL("final", "final"),
    STATIC("static", "{static}"),
    ABSTRACT("abstract", "{abstract}");

    private String USTUML;
    private String plantUML;

    Modifier(String USTUML, String plantUML) {
        this.USTUML = USTUML;
        this.plantUML = plantUML;
    }

    String getUSTUML() {
        return this.USTUML;
    }

    String getPlantUML() {
        return this.plantUML;
    }
}
