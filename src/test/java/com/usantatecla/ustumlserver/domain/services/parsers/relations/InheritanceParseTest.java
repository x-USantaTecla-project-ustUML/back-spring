package com.usantatecla.ustumlserver.domain.services.parsers.relations;

import com.usantatecla.ustumlserver.domain.model.builders.RelationBuilder;

public class InheritanceParseTest extends RelationParserTest {

    @Override
    protected String setRelationType() {
        return RelationParserTest.INHERITANCE;
    }

    @Override
    protected RelationBuilder createBuilderWithRelation() {
        return new RelationBuilder().inheritance();
    }

}
