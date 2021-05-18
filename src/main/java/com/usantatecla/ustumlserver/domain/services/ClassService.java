package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.*;

class ClassService extends MemberService {

    ClassService(Member member) {
        super(member);
    }

    @Override
    public void add(Command command) {

    }

    @Override
    void accept(ServiceVisitor serviceVisitor) {
        serviceVisitor.visit(this);
    }

}
