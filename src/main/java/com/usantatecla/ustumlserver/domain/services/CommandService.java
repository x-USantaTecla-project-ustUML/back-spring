package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class CommandService implements CommandParser {

    private CommandParser commandParser;

    public CommandService() {
    }

    public Member get(JSONObject json) {
        if (this.parse(json).isNull()) {
            return this.get();
        }
        return null;
    }

    @Override
    public Error parse(JSONObject json) {
        if (json.keys().hasNext()) {
            String command = json.keys().next().toString();
            this.commandParser = CommandType.get(command).create();
            JSONArray jsonArray;
            try {
                jsonArray = json.getJSONArray(command);
                for (int i = 0; i < jsonArray.length(); i++){
                    Error error = this.commandParser.parse(jsonArray.getJSONObject(i));
                    if (!error.isNull()){
                        return error;
                    }
                }
            } catch (JSONException e) {
                return Error.INVALID_JSON;
            }
        }
        return Error.NULL;
    }

    @Override
    public Member get() {
        return this.commandParser.get();
    }

}
