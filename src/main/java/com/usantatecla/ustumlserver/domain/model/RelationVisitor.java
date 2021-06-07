package com.usantatecla.ustumlserver.domain.model;

public interface RelationVisitor {

    void visit(Use use);
    void visit(Composition composition);
    void visit(Inheritance inheritance);
    void visit(Aggregation aggregation);
    void visit(Association association);

}
