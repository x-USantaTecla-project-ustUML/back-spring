package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Association;
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
public class AssociationEntity extends RelationEntity{

    public AssociationEntity(Association association, MemberEntity target) {
        BeanUtils.copyProperties(association, this);
        this.setTarget(target);
    }

    public Association toAssociation() {
        Association association = new Association();
        BeanUtils.copyProperties(this, association);
        association.setTarget(this.getTarget().toMemberWithoutRelations());
        return association;
    }

    @Override
    protected Relation toRelation() {
        return this.toAssociation();
    }

}
