package com.usantatecla.ustumlserver.domain.services.parsers.relations;

import com.usantatecla.ustumlserver.domain.model.builders.RelationBuilder;

public class UseParserTest extends RelationParserTest {

    @Override
    protected String setRelationType() {
        return RelationParserTest.USE;
    }

    @Override
    protected RelationBuilder createBuilderWithRelation() {
        return new RelationBuilder().use();
    }
}
