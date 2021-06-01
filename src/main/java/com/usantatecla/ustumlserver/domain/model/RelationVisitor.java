package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;

public interface RelationVisitor {

    void visit(Use use);

}
