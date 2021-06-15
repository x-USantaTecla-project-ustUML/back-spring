package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.domain.model.generators.Generator;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@SuperBuilder
public class Interface extends Class {

    private static final String UML_NAME = "interface";

    public Interface(String name, List<Modifier> modifiers, List<Attribute> attributes) {
        super(name, modifiers, attributes);
    }

    @Override
    public String accept(Generator generator) {
        return generator.visit(this);
    }

    @Override
    public void accept(MemberVisitor memberVisitor) {
        memberVisitor.visit(this);
    }

    @Override
    public String getUstName() {
        return Interface.UML_NAME + ":";
    }

    @Override
    public String getPlantUml() {
        return Interface.UML_NAME;
    }

}
