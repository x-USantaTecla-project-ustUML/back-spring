package com.usantatecla.ustumlserver.domain.services;

public interface ServiceVisitor {

    void visit(PackageService packageService);

    void visit(ClassService classService);

}
