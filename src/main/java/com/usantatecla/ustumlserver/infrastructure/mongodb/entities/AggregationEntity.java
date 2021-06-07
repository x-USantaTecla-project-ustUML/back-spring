package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Aggregation;
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
public class AggregationEntity extends RelationEntity {

    public AggregationEntity(Aggregation aggregation, MemberEntity target) {
        BeanUtils.copyProperties(aggregation, this);
        this.setTarget(target);
    }

    public Aggregation toAggregation() {
        Aggregation aggregation = new Aggregation();
        BeanUtils.copyProperties(this, aggregation);
        aggregation.setTarget(this.getTarget().toMemberWithoutRelations());
        return aggregation;
    }

    @Override
    protected Relation toRelation() {
        return this.toAggregation();
    }

}
