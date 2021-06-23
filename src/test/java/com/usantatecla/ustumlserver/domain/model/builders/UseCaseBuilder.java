package com.usantatecla.ustumlserver.domain.model.builders;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.UseCase;

import java.util.ArrayList;

public class UseCaseBuilder extends MemberBuilder {

    private BuilderContext context;
    protected String id;
    protected String name;
    private UseCaseBuilder useCaseBuilder;

    public UseCaseBuilder() {
        this.context = BuilderContext.ON_ME;
        this.name = "name";
    }

    public UseCaseBuilder(Actor actor) {
        this.context = BuilderContext.ON_ME;
        this.name = actor.getName();
        this.relations = new ArrayList<>(actor.getRelations());
    }

    public UseCaseBuilder id(String id) {
        switch (this.context) {
            case ON_ME:
                this.id = id;
                break;
        }
        return this;
    }

    public UseCaseBuilder name(String name) {
        switch (this.context) {
            case ON_ME:
                this.name = name;
                break;
        }
        return this;
    }

    @Override
    public UseCaseBuilder use() {
        super.use();
        return this;
    }

    @Override
    public UseCaseBuilder target(Member member) {
        super.target(member);
        return this;
    }

    @Override
    public UseCaseBuilder route(String route) {
        super.route(route);
        return this;
    }

    @Override
    public UseCaseBuilder role(String role) {
        super.role(role);
        return this;
    }

    public UseCase build() {
        UseCase useCase = this.createUseCase();
        useCase.setId(this.id);
        this.setRelations(useCase);
        return useCase;
    }

    protected UseCase createUseCase() {
        return new UseCase(this.name);
    }

}
