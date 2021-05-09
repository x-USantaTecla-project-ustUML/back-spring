package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Definition {

    protected String name;
    protected String type;
    protected List<Modifier> modifiers;

    public Definition(String name, String type, List<Modifier> modifiers) {
        this.name = name;
        this.type = type;
        if (!Modifier.isThereVisibility(modifiers)) {
            modifiers.add(0, Modifier.PACKAGE);
        }
        this.modifiers = modifiers;
    }

}
