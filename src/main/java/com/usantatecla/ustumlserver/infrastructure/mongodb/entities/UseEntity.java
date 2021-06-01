package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Package;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

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
        use.setTarget(this.getTarget().toMember());
        return use;
    }

    @Override
    protected Relation toRelation() {
        return this.toUse();
    }

}
