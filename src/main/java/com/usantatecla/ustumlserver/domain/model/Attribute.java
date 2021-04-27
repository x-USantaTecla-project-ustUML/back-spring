package com.usantatecla.ustumlserver.domain.model;

import java.util.List;

class Attribute extends Definition {

    Attribute() {
        super();
    }

    @Override
    void setModifiers(List<Modifier> modifiers) {
        assert !modifiers.contains(Modifier.ABSTRACT);
        super.setModifiers(modifiers);
    }

}
