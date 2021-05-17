package com.usantatecla.ustumlserver.domain.model;


public class RelationBuilder {

    private Relation relation;

    public RelationBuilder use() {
        this.relation = new Use();
        return this;
    }

    public RelationBuilder target(String target) {
        this.relation.setTarget(new ClassBuilder().name(target).build());
        return this;
    }

    public RelationBuilder role(String role) {
        this.relation.setRole(role);
        return this;
    }

    public Relation build() {
        return this.relation;
    }

}
