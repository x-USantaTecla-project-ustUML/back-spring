package com.usantatecla.ustumlserver.domain.services.parsers.relations;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.relations.*;

public enum RelationType {

    USE(new Use()),
    COMPOSITION(new Composition()),
    INHERITANCE(new Inheritance()),
    AGGREGATION(new Aggregation()),
    ASSOCIATION(new Association()),
    NULL;

    Relation relation;

    RelationType(Relation relation) {
        this.relation = relation;
    }

    RelationType() {
    }

    public Relation create(Member target, String role) {
        assert !this.isNull();

        return this.relation.copy(target, role);
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
