package com.usantatecla.ustumlserver.domain.model;

public interface MemberVisitor {

    void visit(Account account);

    void visit(Package pakage);

    void visit(Project project);

    void visit(Class clazz);

    void visit(Interface _interface);

}
