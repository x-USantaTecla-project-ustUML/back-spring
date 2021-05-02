package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

class AddService implements CommandParser {

    static final String MEMBERS = "members";

    private List<Member> members;

    @Override
    public Error parse(JSONObject json) {
        if (json.has(AddService.MEMBERS)) {
            try {
                JSONArray jsonArray = json.getJSONArray(AddService.MEMBERS);

            } catch (JSONException e) {
                return Error.INVALID_JSON;
            }
        }
        return Error.NULL;
    }

    @Override
    public Member get() {
        return null;
    }

}
