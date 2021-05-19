package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;

public abstract class MemberService {

    protected Member member;

    public MemberService(Member member) {
        this.member = member;
    }

    abstract void add(Command command);

    abstract void accept(ServiceVisitor serviceVisitor);

    Member getMember() {
        return this.member;
    }
}
