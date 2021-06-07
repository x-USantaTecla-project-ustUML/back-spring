package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Composition;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.domain.model.Use;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@SuperBuilder
@Document
public class CompositionEntity extends RelationEntity {

    public CompositionEntity(Composition composition, MemberEntity target) {
        BeanUtils.copyProperties(composition, this);
        this.setTarget(target);
    }

    public Composition toComposition() {
        Composition composition = new Composition();
        BeanUtils.copyProperties(this, composition);
        composition.setTarget(this.getTarget().toMemberWithoutRelations());
        return composition;
    }

    @Override
    protected Relation toRelation() {
        return this.toComposition();
    }

}
