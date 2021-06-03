package com.usantatecla.ustumlserver.domain.model.builders;

import com.usantatecla.ustumlserver.domain.model.Definition;
import com.usantatecla.ustumlserver.domain.model.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class DefinitionBuilder {

    protected String name;
    protected String type;
    protected List<Modifier> modifiers;

    DefinitionBuilder() {
        this.name = "name";
        this.type = "Type";
        this.modifiers = new ArrayList<>();
    }

    DefinitionBuilder name(String name) {
        this.name = name;
        return this;
    }

    DefinitionBuilder type(String type) {
        this.type = type;
        return this;
    }

    DefinitionBuilder modifiers(Modifier... modifiers) {
        this.modifiers.addAll(Arrays.asList(modifiers));
        return this;
    }

    abstract Definition build();

}
