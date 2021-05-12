package com.usantatecla.ustumlserver.domain.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Command {

    static final String MEMBERS = "members";

    private JSONObject jsonObject;

    public Command(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    boolean has(String key) {
        return jsonObject.has(key);
    }

    CommandType getCommandType() {
        CommandType commandType = CommandType.NULL;
        if (this.jsonObject.keys().hasNext()) {
            String command = this.jsonObject.keys().next().toString();
            commandType = CommandType.get(command);
            if (commandType.isNull()) {
                throw new CommandParserException(Error.COMMAND_NOT_FOUND, command);
            }
        }
        return commandType;
    }

    MemberType getMemberType() {
        MemberType memberType;
        Iterator<Object> iterator = this.jsonObject.keys();
        while (iterator.hasNext()) {
            memberType = MemberType.get(iterator.next().toString());
            if (!memberType.isNull()) {
                return memberType;
            }
        }
        throw new CommandParserException(Error.MEMBER_NOT_FOUND, this.jsonObject.toString());
    }

    List<Command> getMembers() {
        Command command = new Command(this.getJSONObject(this.getCommandType().getName()));
        return command.getCommands(Command.MEMBERS);
    }

    List<Command> getCommands(String key) {
        List<Command> commands = new ArrayList<>();
        JSONArray members = this.getJSONArray(key);
        for (int i = 0; i < members.length(); i++) {
            commands.add(new Command(this.getJSONObject(i, members)));
        }
        return commands;
    }

    String getMemberName() {
        return this.getString(this.getMemberType().getName());
    }

    String getString(String key) {
        if (this.jsonObject.has(key)) {
            try {
                return this.jsonObject.getString(key);
            } catch (JSONException e) {
                throw new CommandParserException(Error.INVALID_VALUE, key);
            }
        } else {
            throw new CommandParserException(Error.KEY_NOT_FOUND, key);
        }
    }

    private JSONArray getJSONArray(String key) {
        if (this.jsonObject.has(key)) {
            try {
                return this.jsonObject.getJSONArray(key);
            } catch (JSONException e) {
                throw new CommandParserException(Error.INVALID_VALUE, key);
            }
        } else {
            throw new CommandParserException(Error.KEY_NOT_FOUND, key);
        }
    }

    private JSONObject getJSONObject(String key) {
        if (this.jsonObject.has(key)) {
            try {
                return this.jsonObject.getJSONObject(key);
            } catch (JSONException e) {
                throw new CommandParserException(Error.INVALID_VALUE, key);
            }
        } else {
            throw new CommandParserException(Error.KEY_NOT_FOUND, key);
        }
    }

    private JSONObject getJSONObject(int index, JSONArray jsonArray) {
        try {
            return jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            throw new CommandParserException(Error.INVALID_ARRAY_VALUE);
        }
    }

}
