package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.builders.RelationBuilder;

public class AssociationParserTest extends RelationParserTest{

    @Override
    protected String setRelationType() {
        return RelationParserTest.ASSOCIATION;
    }

    @Override
    protected RelationBuilder createBuilderWithRelation() {
        return new RelationBuilder().association();
    }
}
