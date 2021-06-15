package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.domain.model.generators.DirectoryTreeGenerator;
import com.usantatecla.ustumlserver.domain.model.generators.Generator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Class extends Member {

    private static final String UML_NAME = "class";

    private List<Modifier> modifiers;
    private List<Attribute> attributes;
    private List<Method> methods;

    public Class(String name, List<Modifier> modifiers, List<Attribute> attributes) {
        super(name);
        if (!Modifier.isThereVisibility(modifiers)) {
            modifiers.add(0, Modifier.PACKAGE);
        }
        this.modifiers = modifiers;
        this.attributes = attributes;
        this.methods = new ArrayList<>();
    }

    public static boolean matchesModifiers(String modifiers) {
        return modifiers.matches("((" + Modifier.PUBLIC.getUstUML() + "( +" + Modifier.ABSTRACT.getUstUML()
                + ")?)|(" + Modifier.PACKAGE.getUstUML() + "( +" + Modifier.ABSTRACT.getUstUML() + ")?)|"
                + Modifier.ABSTRACT.getUstUML() + ")");
    }

    public void addAttributes(List<Attribute> attributes) {
        this.attributes.addAll(attributes);
    }

    public void addMethods(List<Method> methods) {
        this.methods.addAll(methods);
    }

    public void modifyAttributes(List<Attribute> attributes) {
        this.attributes.remove(attributes.get(0));
        this.attributes.add(attributes.get(1));
    }

    public void modifyMethods(List<Method> methods) {
        this.methods.remove(methods.get(0));
        this.methods.add(methods.get(1));
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
        return Class.UML_NAME + ":";
    }

    @Override
    public String getPlantUml() {
        return Class.UML_NAME;
    }

    @Override
    public String accept(DirectoryTreeGenerator directoryTreeGenerator) {
        return directoryTreeGenerator.visit(this);
    }

}
