package com.usantatecla.ustumlserver.domain.services;

enum MemberType {

    PACKAGE(new PackageParser()),
    CLASS(new ClassParser()),
    NULL;

    MemberParser memberParser;

    MemberType(MemberParser memberParser) {
        this.memberParser = memberParser;
    }

    MemberType() {
    }

    MemberParser create() {
        assert !this.isNull();

        return this.memberParser.copy();
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
