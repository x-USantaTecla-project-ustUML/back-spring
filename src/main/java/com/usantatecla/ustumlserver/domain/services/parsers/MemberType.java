package com.usantatecla.ustumlserver.domain.services.parsers;

public enum MemberType {

    PROJECT(new ProjectParser()),
    PACKAGE(new PackageParser()),
    CLASS(new ClassParser()),
    NULL;

    MemberParser memberParser;

    MemberType(MemberParser memberParser) {
        this.memberParser = memberParser;
    }

    MemberType() {
    }

    public MemberParser create() {
        assert !this.isNull();

        return this.memberParser.copy();
    }

    public boolean isNull() {
        return this == MemberType.NULL;
    }

    public static MemberType get(String member) {
        for (MemberType memberType : MemberType.values()) {
            if (memberType.getName().equals(member)) {
                return memberType;
            }
        }
        return MemberType.NULL;
    }

    public String getName() {
        return this.name().toLowerCase();
    }

}
