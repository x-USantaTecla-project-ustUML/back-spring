package com.usantatecla.ustumlserver.domain.services.interpreters;

public interface InterpreterVisitor {

    void visit(ProjectInterpreter projectInterpreter);

    void visit(PackageInterpreter packageService);

    void visit(ClassInterpreter classInterpreter);
}
