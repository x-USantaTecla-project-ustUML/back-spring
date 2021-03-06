package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.services.parsers.classDiagram.ClassParser;
import com.usantatecla.ustumlserver.domain.services.parsers.classDiagram.EnumParser;
import com.usantatecla.ustumlserver.domain.services.parsers.classDiagram.InterfaceParser;
import com.usantatecla.ustumlserver.domain.services.parsers.useCaseDiagram.ActorParser;
import com.usantatecla.ustumlserver.domain.services.parsers.useCaseDiagram.UseCaseParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

public enum MemberType {

    PROJECT,
    PACKAGE(new PackageParser()),
    CLASS(new ClassParser()),
    INTERFACE(new InterfaceParser()),
    ENUM(new EnumParser()),
    ACTOR(new ActorParser()),
    USECASE(new UseCaseParser()),
    NULL;

    MemberParser memberParser;

    MemberType(MemberParser memberParser) {
        this.memberParser = memberParser;
    }

    MemberType() {
    }

    public MemberParser create(Account account) {
        assert !this.isNull();

        if (this.memberParser == null) {
            throw new ParserException(ErrorMessage.MEMBER_NOT_ALLOWED, this.getName());
        }
        return this.memberParser.copy(account);
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
