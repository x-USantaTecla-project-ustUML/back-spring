package com.usantatecla.ustumlserver.domain.services.parsers.relations;

import com.usantatecla.ustumlserver.domain.model.builders.RelationBuilder;

public class AggregationParserTest extends RelationParserTest {

    @Override
    protected String setRelationType() {
        return RelationParserTest.AGGREGATION;
    }

    @Override
    protected RelationBuilder createBuilderWithRelation() {
        return new RelationBuilder().aggregation();
    }
}
