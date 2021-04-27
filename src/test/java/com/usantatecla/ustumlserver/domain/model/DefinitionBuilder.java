package com.usantatecla.ustumlserver.domain.model;

import java.util.Arrays;

abstract class DefinitionBuilder {

    protected Definition definition;

    DefinitionBuilder name(String name) {
        this.definition.setName(name);
        return this;
    }

    DefinitionBuilder type(String type) {
        this.definition.setType(type);
        return this;
    }

    DefinitionBuilder modifiers(Modifier... modifiers) {
        this.definition.setModifiers(Arrays.asList(modifiers));
        return this;
    }

    Definition build() {
        return this.definition;
    }

}
