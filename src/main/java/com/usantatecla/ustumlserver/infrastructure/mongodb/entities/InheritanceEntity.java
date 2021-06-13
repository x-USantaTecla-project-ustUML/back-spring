package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Inheritance;
import com.usantatecla.ustumlserver.domain.model.Relation;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@SuperBuilder
@Document
public class InheritanceEntity extends RelationEntity {

    public InheritanceEntity(Inheritance inheritance, MemberEntity target) {
        BeanUtils.copyProperties(inheritance, this);
        this.setTarget(target);
    }

    public Inheritance toInheritance() {
        Inheritance inheritance = new Inheritance();
        BeanUtils.copyProperties(this, inheritance);
        inheritance.setTarget(this.getTarget().toMemberWithoutRelations());
        return inheritance;
    }

    @Override
    protected Relation toRelation() {
        return this.toInheritance();
    }

}
