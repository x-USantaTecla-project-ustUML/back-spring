package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.builders.RelationBuilder;

public class CompositionParserTest extends RelationParserTest {

    @Override
    protected String setRelationType() {
        return RelationParserTest.COMPOSITION;
    }

    @Override
    protected RelationBuilder createBuilderWithRelation() {
        return new RelationBuilder().composition();
    }
}
