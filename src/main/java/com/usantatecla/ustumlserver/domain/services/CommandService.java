package com.usantatecla.ustumlserver.domain.services;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class CommandService extends CommandParser {

    @Override
    public Error parse(JSONObject json) {
        if (json.keys().hasNext()) {
            String command = json.keys().next().toString();
            JSONObject jsonObject = new JSONKeyFinder(json).getJSONObject(command);
            CommandType commandType = CommandType.get(command);
            if (commandType.isNull()) {
                return Error.COMMAND_NOT_FOUND;
            }
            this.member = commandType.create().get(jsonObject);
        }
        return Error.NULL;
    }

}
