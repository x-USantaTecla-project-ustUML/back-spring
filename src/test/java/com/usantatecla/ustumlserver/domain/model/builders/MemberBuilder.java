package com.usantatecla.ustumlserver.domain.model.builders;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;

import java.util.ArrayList;
import java.util.List;

public abstract class MemberBuilder {

    protected List<Relation> relations;
    protected RelationBuilder relationBuilder;
    protected BuilderContext context;

    MemberBuilder(){
        this.relations = new ArrayList<>();
    }

    public MemberBuilder use(){
        if (this.context == BuilderContext.ON_RELATION) {
            this.relations.add(this.relationBuilder.build());
        } else this.context = BuilderContext.ON_RELATION;
        this.relationBuilder = new RelationBuilder().use();
        return this;
    }

    public MemberBuilder target(Member member){
        this.relationBuilder.target(member);
        return this;
    }

    public MemberBuilder route(String route){
        this.relationBuilder.route(route);
        return this;
    }

    public MemberBuilder role(String role){
        this.relationBuilder.role(role);
        return this;
    }

    public void setRelations(Member member) {
        if (this.relationBuilder != null) {
            this.relations.add(this.relationBuilder.build());
        }
        member.setRelations(this.relations);
    }

}
