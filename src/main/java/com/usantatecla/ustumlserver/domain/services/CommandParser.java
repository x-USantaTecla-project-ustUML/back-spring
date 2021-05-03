package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import org.json.JSONObject;

abstract class CommandParser {

    protected Member member;

    public Member get(JSONObject json) {
        Error error = this.parse(json);
        if (!error.isNull()) {
            throw new CommandParserException(error.getDetail());
        }
        return this.member;
    }

    abstract Error parse(JSONObject json);

}
