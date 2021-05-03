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
        assert this.commandParserCreator != null;

        return this.commandParserCreator.get();
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
