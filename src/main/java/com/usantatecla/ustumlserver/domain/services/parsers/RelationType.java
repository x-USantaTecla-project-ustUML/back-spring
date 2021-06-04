package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.domain.model.Use;
import com.usantatecla.ustumlserver.domain.persistence.AccountPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;

public enum RelationType {

    USE(new Use()),
    NULL;

    Relation relation;

    RelationType(Relation relation) {
        this.relation = relation;
    }

    RelationType() {
    }

    public Relation create(Command relationCommand, Account account) {
        assert !this.isNull();

        return new RelationParser().get(relation, relationCommand, account);
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
