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
        return attribute.matches("((" + Modifier.PUBLIC.getUstUML() + " |" + Modifier.PACKAGE.getUstUML()
                + " |" + Modifier.PRIVATE.getUstUML() + " |" + Modifier.PROTECTED.getUstUML()
                + " )?( +)?(" + Modifier.STATIC.getUstUML() + " )?( +)?(" + Modifier.FINAL.getUstUML() + " )?( +)?"
                + Member.NAME_REGEX + "( +" + Member.NAME_REGEX + "))");
    }

}
