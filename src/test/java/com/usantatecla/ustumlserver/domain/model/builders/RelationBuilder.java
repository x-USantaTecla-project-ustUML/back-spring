package com.usantatecla.ustumlserver.domain.model.builders;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.domain.model.Use;

public class RelationBuilder {

    private Relation relation;

    public RelationBuilder use() {
        this.relation = new Use();
        this.relation.setRole("");
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

    public Relation build() {
        assert this.relation != null;
        return this.relation;
    }

}
