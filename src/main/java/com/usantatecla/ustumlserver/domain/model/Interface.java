package com.usantatecla.ustumlserver.domain.model;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class Interface extends Class {

    private static final String UST_NAME = "class:";

    public Interface(String name, List<Modifier> modifiers, List<Attribute> attributes) {
        super(name, modifiers, attributes);
    }

    @Override
    public void accept(MemberVisitor memberVisitor) {
        memberVisitor.visit(this);
    }

    @Override
    public String getUstName() {
        return Interface.UST_NAME;
    }

}
