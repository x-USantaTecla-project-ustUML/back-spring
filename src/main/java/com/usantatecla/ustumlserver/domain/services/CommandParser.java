package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import org.json.JSONObject;

public interface CommandParser {

    Error parse(JSONObject json);

    Member get();
}
