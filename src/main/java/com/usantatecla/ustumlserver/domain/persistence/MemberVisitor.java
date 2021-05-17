package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;

public interface MemberVisitor {

    void visit(Package pakage);
    void visit(Class clazz);

}
