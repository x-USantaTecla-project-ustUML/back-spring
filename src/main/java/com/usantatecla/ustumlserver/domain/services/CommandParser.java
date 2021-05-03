package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import org.json.JSONObject;

abstract class CommandParser {

    protected Member member;

    public Member get(JSONObject json) {
        if (!this.parse(json).isNull()) {
            return null;
        }
        return this.member;
    }

    abstract Error parse(JSONObject json);

}
