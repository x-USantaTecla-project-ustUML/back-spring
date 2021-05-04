package com.usantatecla.ustumlserver.domain.services;

import java.util.function.Supplier;

enum MemberType {

    CLASS(new ClassService()),
    NULL;

    MemberService memberService;

    MemberType(MemberService memberService) {
        this.memberService = memberService;
    }

    MemberType() {
    }

    MemberService create() {
        assert !this.isNull();

        return this.memberService;
    }

    boolean isNull() {
        return this == MemberType.NULL;
    }

    static MemberType get(String member) {
        for (MemberType memberType : MemberType.values()) {
            if (memberType.name().toLowerCase().equals(member)) {
                return memberType;
            }
        }
        return MemberType.NULL;
    }

}
