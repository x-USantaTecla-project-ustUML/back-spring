package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

public enum MemberType {

    PROJECT,
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

        if (this.memberParser == null) {
            throw new ParserException(ErrorMessage.MEMBER_NOT_ALLOWED, this.getName());
        }
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
