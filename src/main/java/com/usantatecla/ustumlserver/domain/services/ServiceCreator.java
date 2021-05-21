package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.MemberVisitor;
import com.usantatecla.ustumlserver.domain.model.Package;

class ServiceCreator implements MemberVisitor {

    private MemberService memberService;

    MemberService create(Member member) {
        member.accept(this);
        return this.memberService;
    }

    @Override
    public void visit(Package pakage) {
        this.memberService = new PackageService(pakage);
    }

    @Override
    public void visit(Class clazz) {
        this.memberService = new ClassService(clazz);
    }

}
