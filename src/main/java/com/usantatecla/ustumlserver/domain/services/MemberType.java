package com.usantatecla.ustumlserver.domain.services;

enum MemberType {

    PACKAGE(new PackageService()),
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

        return this.memberService.copy();
    }

    boolean isNull() {
        return this == MemberType.NULL;
    }

    static MemberType get(String member) {
        for (MemberType memberType : MemberType.values()) {
            if (memberType.getName().equals(member)) {
                return memberType;
            }
        }
        return MemberType.NULL;
    }

    String getName() {
        return this.name().toLowerCase();
    }
}
