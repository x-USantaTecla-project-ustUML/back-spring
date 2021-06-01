package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class Use extends Relation {

    public Use(Member target, String role) {
        super(target, role);
    }

    @Override
    public void accept(RelationVisitor relationVisitor, MemberEntity memberEntity) {
        relationVisitor.visit(this, memberEntity);
    }

    @Override
    public String accept(Generator generator, Member origin) {
        return generator.visit(this, origin);
    }
}
