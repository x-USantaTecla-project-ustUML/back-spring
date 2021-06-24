package com.usantatecla.ustumlserver.domain.services.parsers.classDiagram;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

public class EnumObjectParser {

    private static final String OBJECT_KEY = "object";

    public String get(Command command) {
        String object = command.getString(EnumObjectParser.OBJECT_KEY);
        if (!Member.matchesName(object)) {
            throw new ParserException(ErrorMessage.INVALID_NAME, object);
        }
        return object;
    }

}
