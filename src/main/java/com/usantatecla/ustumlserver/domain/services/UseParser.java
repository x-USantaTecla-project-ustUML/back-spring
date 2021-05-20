package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.domain.model.Use;

public class UseParser extends RelationParser {

    @Override
    protected String getTargetName(Command command) {
        return command.getString(RelationType.USE.getName());
    }

    @Override
    protected Relation create() {
        return new Use(this.target, this.role);
    }

    @Override
    public UseParser copy() {
        return new UseParser();
    }

}
