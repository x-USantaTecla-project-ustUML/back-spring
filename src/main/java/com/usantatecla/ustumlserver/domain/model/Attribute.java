package com.usantatecla.ustumlserver.domain.model;

import java.util.List;

public class Attribute extends Definition {

    Attribute(String name, String type, List<Modifier> modifiers) {
        super(name, type, modifiers);
        assert !modifiers.contains(Modifier.ABSTRACT);
    }

}
