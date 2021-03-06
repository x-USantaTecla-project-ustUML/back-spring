package com.usantatecla.ustumlserver.domain.model.classDiagram;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Method extends Definition {

    private List<Parameter> parameters;

    public Method(String name, String type, List<Modifier> modifiers) {
        super(name, type, modifiers);
        this.parameters = new ArrayList<>();
    }

    public static boolean matches(String method) {
        return method.matches("((" + Modifier.PUBLIC.getUstUML() + " |" + Modifier.PACKAGE.getUstUML() + " |"
                + Modifier.PRIVATE.getUstUML() + " )?( +)?(" + Modifier.ABSTRACT.getUstUML() + " |"
                + Modifier.STATIC.getUstUML() + " )?( +)?(" + Definition.TYPE_REGEX + " +" + Definition.NAME_REGEX + ")\\((("
                + Definition.TYPE_REGEX + " +" + Definition.NAME_REGEX + ")(, +(" + Definition.TYPE_REGEX + " +"
                + Definition.NAME_REGEX + ")+)?)?\\))");
    }

}
