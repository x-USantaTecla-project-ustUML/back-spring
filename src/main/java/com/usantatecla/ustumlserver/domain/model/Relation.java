package com.usantatecla.ustumlserver.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Relation {

    protected Member target;
    protected String role;

    public Relation() {
        this.role = "";
    }

}
