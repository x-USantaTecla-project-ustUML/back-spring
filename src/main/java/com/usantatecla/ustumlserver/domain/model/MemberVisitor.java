package com.usantatecla.ustumlserver.domain.model;

public interface MemberVisitor {
    void visit(Package pakage);

    void visit(Class clazz);

}
