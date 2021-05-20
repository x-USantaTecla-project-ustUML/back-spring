package com.usantatecla.ustumlserver.domain.services;

enum RelationType {

    USE(new UseParser()),
    NULL;

    RelationParser relationParser;

    RelationType(RelationParser relationParser) {
        this.relationParser = relationParser;
    }

    RelationType() {
    }

    RelationParser create() {
        assert !this.isNull();

        return this.relationParser.copy();
    }

    boolean isNull() {
        return this == RelationType.NULL;
    }

    static RelationType get(String relation) {
        for (RelationType relationType : RelationType.values()) {
            if (relationType.getName().equals(relation)) {
                return relationType;
            }
        }
        return RelationType.NULL;
    }

    String getName() {
        return this.name().toLowerCase();
    }

}
