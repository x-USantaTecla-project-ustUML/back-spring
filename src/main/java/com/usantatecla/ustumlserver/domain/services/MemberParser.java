package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;

abstract class MemberParser {

    protected String name;

    public abstract Member get(Command command);

    protected void parseName(Command command) {
        String name = command.getMemberName();
        if (Package.matchesName(name)) {
            this.name = name;
        } else {
            throw new CommandParserException(Error.INVALID_NAME, name);
        }
    }

    public abstract MemberParser copy();

}
