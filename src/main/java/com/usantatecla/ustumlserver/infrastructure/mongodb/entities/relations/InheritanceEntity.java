package com.usantatecla.ustumlserver.infrastructure.mongodb.entities.relations;

import com.usantatecla.ustumlserver.domain.model.relations.Inheritance;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
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
    public Relation toRelation() {
        return this.toInheritance();
    }

}
