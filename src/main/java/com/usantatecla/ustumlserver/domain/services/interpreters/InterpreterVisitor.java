package com.usantatecla.ustumlserver.domain.services.interpreters;

public interface InterpreterVisitor {

    void visit(PackageInterpreter packageService);

    void visit(ClassInterpreter classInterpreter);

}
