package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Method extends Definition {

    private List<Parameter> parameters;

    public Method(String name, String type, List<Modifier> modifiers) {
        super(name, type, modifiers);
    }

    public static boolean matches(String method) {
        return method.matches("((" + Modifier.PUBLIC.getUstUML() + " |" + Modifier.PACKAGE.getUstUML() + " |"
                + Modifier.PRIVATE.getPlantUML() + " )?( +)?(" + Modifier.ABSTRACT.getUstUML() + " |"
                + Modifier.STATIC.getUstUML() + " )?( +)?(" + Member.NAME_REGEX + " +" + Member.NAME_REGEX + ")\\((("
                + Member.NAME_REGEX + " +" + Member.NAME_REGEX + ")(, +(" + Member.NAME_REGEX + " +"
                + Member.NAME_REGEX + ")+)?)?\\))");
    }

}
