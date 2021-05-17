package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;

abstract class MemberParser {

    public abstract Member get(Command command);

    protected String getName(Command command) {
        String name = command.getMemberName();
        if (Package.matchesName(name)) {
            return name;
        } else {
            throw new CommandParserException(Error.INVALID_NAME, name);
        }
    }

    public abstract MemberParser copy();

}
