package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.MemberVisitor;
import com.usantatecla.ustumlserver.domain.model.Package;

public class MemberAcceptor implements MemberVisitor {

    private MemberService memberService;

    @Override
    public void visit(Package pakage) {
        this.memberService = new PackageService(pakage);
    }

    @Override
    public void visit(Class clazz) {
        this.memberService = new ClassService(clazz);
    }

    MemberService get() {
        return this.memberService;
    }

}
