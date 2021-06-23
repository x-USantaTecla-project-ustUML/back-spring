package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorPersistence extends MemberPersistence {
    Actor read(String id);
    Actor update(Actor actor);
}
