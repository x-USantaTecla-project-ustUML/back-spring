package com.usantatecla.ustumlserver.domain.services.interpreters;

public interface InterpreterVisitor {

    void visit(AccountInterpreter accountInterpreter);

    void visit(PackageInterpreter packageService);

    void visit(ClassInterpreter classInterpreter);
}
