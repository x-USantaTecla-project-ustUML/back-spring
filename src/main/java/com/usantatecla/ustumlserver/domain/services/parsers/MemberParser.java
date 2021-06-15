package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

public abstract class MemberParser {

    public static final String SET_KEY = "set";

    protected String name;

    public abstract Member get(Command command);

    protected void parseName(String name) {
        if (!name.equals("null") && Member.matchesName(name)) {
            this.name = name;
        } else {
            throw new ParserException(ErrorMessage.INVALID_NAME, name);
        }
    }

    public abstract MemberParser copy();

}
