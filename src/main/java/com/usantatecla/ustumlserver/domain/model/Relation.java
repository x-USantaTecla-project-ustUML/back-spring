package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
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

    public abstract void accept(RelationVisitor relationVisitor, MemberEntity memberEntity);

    public abstract String accept(Generator generator, Member origin);

}
