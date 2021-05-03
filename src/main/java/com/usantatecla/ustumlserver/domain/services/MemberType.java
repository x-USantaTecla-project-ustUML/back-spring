package com.usantatecla.ustumlserver.domain.services;

import java.util.function.Supplier;

enum MemberType {

    CLASS(ClassService::new),
    NULL;

    Supplier<CommandParser> commandParserCreator;

    MemberType(Supplier<CommandParser> commandParserCreator) {
        this.commandParserCreator = commandParserCreator;
    }

    MemberType() {
    }

    CommandParser create() {
        assert !this.isNull();

        return this.commandParserCreator.get();
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
