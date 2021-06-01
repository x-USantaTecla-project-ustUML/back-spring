package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

public enum RelationType {

    USE(new UseParser()),
    NULL;

    RelationParser relationParser;

    RelationType(RelationParser relationParser) {
        this.relationParser = relationParser;
    }

    RelationType() {
    }

    public RelationParser create() {
        assert !this.isNull();

        if (this.relationParser == null) {
            throw new ParserException(ErrorMessage.MEMBER_NOT_ALLOWED, this.getName());
        }
        return this.relationParser.copy();
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
