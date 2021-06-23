package com.usantatecla.ustumlserver.infrastructure.mongodb.entities.relations;

import com.usantatecla.ustumlserver.domain.model.relations.Relation;
import com.usantatecla.ustumlserver.domain.model.relations.Use;
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
public class UseEntity extends RelationEntity {

    public UseEntity(Use use, MemberEntity target) {
        BeanUtils.copyProperties(use, this);
        this.setTarget(target);
    }

    public Use toUse() {
        Use use = new Use();
        BeanUtils.copyProperties(this, use);
        use.setTarget(this.getTarget().toMemberWithoutRelations());
        return use;
    }

    @Override
    public Relation toRelation() {
        return this.toUse();
    }

}
