package com.usantatecla.ustumlserver.infrastructure.mongodb.entities.useCaseDiagram;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@SuperBuilder
@AllArgsConstructor
@Document
public class ActorEntity extends MemberEntity {

    public ActorEntity(Actor actor) {
        BeanUtils.copyProperties(actor, this);
    }

    @Override
    protected Member toMember() {
        return this.toActor();
    }

    @Override
    public Member toMemberWithoutRelations() {
        Actor actor = this.createActor();
        BeanUtils.copyProperties(this, actor);
        actor.setRelations(new ArrayList<>());
        return actor;
    }

    protected Actor createActor() {
        return new Actor();
    }

    public Actor toActor() {
        Actor actor = (Actor) this.toMemberWithoutRelations();
        actor.setRelations(this.getRelations());
        return actor;
    }
}
