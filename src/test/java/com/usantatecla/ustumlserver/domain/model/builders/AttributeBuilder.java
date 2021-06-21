package com.usantatecla.ustumlserver.domain.model.builders;

import com.usantatecla.ustumlserver.domain.model.classDiagram.Attribute;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Modifier;

public class AttributeBuilder extends DefinitionBuilder {

    public AttributeBuilder() {
        super();
    }

    @Override
    AttributeBuilder name(String name) {
        super.name(name);
        return this;
    }

    @Override
    AttributeBuilder type(String type) {
        super.type(type);
        return this;
    }

    @Override
    public AttributeBuilder modifiers(Modifier... modifiers) {
        super.modifiers(modifiers);
        return this;
    }

    @Override
    public Attribute build() {
        return new Attribute(this.name, this.type, this.modifiers);
    }

}
