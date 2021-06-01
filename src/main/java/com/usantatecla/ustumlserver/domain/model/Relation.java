package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Data
public abstract class Relation {

    protected String id;
    private Member target;
    private String role;

    public Relation(Member target, String role) {
        this.target = target;
        this.role = role;
    }

    public abstract void accept(RelationVisitor relationVisitor);

}
