package com.usantatecla.ustumlserver.domain.model.builders;

import com.usantatecla.ustumlserver.domain.model.*;

public class RelationBuilder {

    private Relation relation;

    public RelationBuilder use() {
        this.relation = new Use();
        this.relation.setRole("");
        return this;
    }

    public RelationBuilder composition() {
        this.relation = new Composition();
        this.relation.setRole("");
        return this;
    }

    public RelationBuilder aggregation() {
        this.relation = new Aggregation();
        this.relation.setRole("");
        return this;
    }

    public RelationBuilder inheritance() {
        this.relation = new Inheritance();
        this.relation.setRole("");
        return this;
    }

    public RelationBuilder association() {
        this.relation = new Association();
        return this;
    }

    public RelationBuilder target(Member target) {
        this.relation.setTarget(target);
        return this;
    }

    public RelationBuilder role(String role) {
        this.relation.setRole(role);
        return this;
    }

    public RelationBuilder route(String route) {
        this.relation.setTargetRoute(route);
        return this;
    }

    public Relation build() {
        assert this.relation != null;
        return this.relation;
    }

}
