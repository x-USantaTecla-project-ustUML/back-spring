package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
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
        if (modifiers.isEmpty()) {
            this.modifiers = Collections.singletonList(Modifier.PACKAGE);
        } else this.modifiers = modifiers;
    }

}
