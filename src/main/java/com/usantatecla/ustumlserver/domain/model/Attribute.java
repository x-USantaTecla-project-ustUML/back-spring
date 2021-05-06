package com.usantatecla.ustumlserver.domain.model;

import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class Attribute extends Definition {

    public Attribute(String name, String type, List<Modifier> modifiers) {
        super(name, type, modifiers);
        assert !modifiers.contains(Modifier.ABSTRACT);
    }

    public static boolean matches(String attribute) {
        return attribute.matches("((public |package |private |protected )?( +)?(static )?( +)?(final )?( +)?" + Member.NAME_REGEX + "( +" + Member.NAME_REGEX + "))");
    }

}
