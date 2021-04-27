package com.usantatecla.ustumlserver.domain.model;

class AttributeBuilder extends DefinitionBuilder {

    AttributeBuilder() {
        this.definition = new Attribute();
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
    AttributeBuilder modifiers(Modifier... modifiers) {
        super.modifiers(modifiers);
        return this;
    }

}
