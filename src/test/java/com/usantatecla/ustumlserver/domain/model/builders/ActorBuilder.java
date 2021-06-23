package com.usantatecla.ustumlserver.domain.model.builders;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActorBuilder extends MemberBuilder {

    private BuilderContext context;
    protected String id;
    protected String name;
    private ActorBuilder actorBuilder;

    public ActorBuilder() {
        this.context = BuilderContext.ON_ME;
        this.name = "name";
    }

    public ActorBuilder(Actor actor) {
        this.context = BuilderContext.ON_ME;
        this.name = actor.getName();
        this.relations = new ArrayList<>(actor.getRelations());
    }

    public ActorBuilder id(String id) {
        switch (this.context) {
            case ON_ME:
                this.id = id;
                break;
        }
        return this;
    }

    public ActorBuilder name(String name) {
        switch (this.context) {
            case ON_ME:
                this.name = name;
                break;
        }
        return this;
    }

    @Override
    public ActorBuilder use() {
        super.use();
        return this;
    }

    @Override
    public ActorBuilder target(Member member) {
        super.target(member);
        return this;
    }

    @Override
    public ActorBuilder route(String route) {
        super.route(route);
        return this;
    }

    @Override
    public ActorBuilder role(String role) {
        super.role(role);
        return this;
    }

    public Actor build() {
        Actor actor = this.createActor();
        actor.setId(this.id);
        this.setRelations(actor);
        return actor;
    }

    protected Actor createActor() {
        return new Actor(this.name);
    }

}
