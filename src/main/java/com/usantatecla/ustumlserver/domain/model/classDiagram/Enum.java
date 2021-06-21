package com.usantatecla.ustumlserver.domain.model.classDiagram;

import com.usantatecla.ustumlserver.domain.model.MemberVisitor;
import com.usantatecla.ustumlserver.domain.model.generators.Generator;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class Enum extends Class {

    private static final String UML_NAME = "enum";

    private List<String> objects;

    public Enum(String name, List<Modifier> modifiers, List<Attribute> attributes) {
        super(name, modifiers, attributes);
        this.objects = new ArrayList<>();
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
        return Enum.UML_NAME + ":";
    }

    @Override
    public String getPlantUml() {
        return Enum.UML_NAME;
    }

}
