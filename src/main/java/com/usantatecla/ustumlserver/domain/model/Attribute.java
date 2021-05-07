package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Attribute extends Definition {

    public Attribute(String name, String type, List<Modifier> modifiers) {
        super(name, type, modifiers);
    }

    public static boolean matches(String attribute) {
        //TODO ARREGLAR EXPRESION
        return attribute.matches("((public |package |private |protected )?( +)?(static )?( +)?(final )?( +)?" + Member.NAME_REGEX + "( +" + Member.NAME_REGEX + "))");
    }

}
