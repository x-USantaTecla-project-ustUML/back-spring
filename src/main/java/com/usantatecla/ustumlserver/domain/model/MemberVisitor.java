package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Interface;

public interface MemberVisitor {

    void visit(Account account);

    void visit(Package pakage);

    void visit(Project project);

    void visit(Class clazz);

    void visit(Interface _interface);

    void visit(Enum _enum);

}
