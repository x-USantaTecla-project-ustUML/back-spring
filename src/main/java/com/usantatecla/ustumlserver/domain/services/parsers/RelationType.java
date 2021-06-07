package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.domain.model.Use;

public enum RelationType {

    USE(new Use()),
    NULL;

    Relation relation;

    RelationType(Relation relation) {
        this.relation = relation;
    }

    RelationType() {
    }

    public Relation create(Member target, String role, String targetRoute) {
        assert !this.isNull();

        return this.relation.copy(target, role, targetRoute);
    }

    public boolean isNull() {
        return this == RelationType.NULL;
    }

    public static RelationType get(String member) {
        for (RelationType relationType : RelationType.values()) {
            if (relationType.getName().equals(member)) {
                return relationType;
            }
        }
        return RelationType.NULL;
    }

    public String getName() {
        return this.name().toLowerCase();
    }

}
