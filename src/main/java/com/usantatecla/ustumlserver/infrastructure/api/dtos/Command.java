package com.usantatecla.ustumlserver.infrastructure.api.dtos;

import com.usantatecla.ustumlserver.domain.services.parsers.MemberType;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Command {

    static final String MEMBERS = "members";

    private JSONObject jsonObject;

    public Command(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public boolean has(String key) {
        return jsonObject.has(key);
    }

    public CommandType getCommandType() {
        CommandType commandType = CommandType.NULL;
        if (this.jsonObject.keys().hasNext()) {
            String command = this.jsonObject.keys().next().toString();
            commandType = CommandType.get(command);
            if (commandType.isNull()) {
                throw new ParserException(ErrorMessage.COMMAND_NOT_FOUND, command);
            }
        }
        return commandType;
    }

    public MemberType getMemberType() {
        MemberType memberType;
        Iterator<Object> iterator = this.jsonObject.keys();
        while (iterator.hasNext()) {
            memberType = MemberType.get(iterator.next().toString());
            if (!memberType.isNull()) {
                return memberType;
            }
        }
        throw new ParserException(ErrorMessage.MEMBER_TYPE_NOT_FOUND, this.jsonObject.toString());
    }

    public Command getMember() {
        return new Command(this.getJSONObject(this.getCommandType().getName()));
    }

    List<Command> getMembers() {
        Command command = this.getMember();
        return command.getCommands(Command.MEMBERS);
    }

    public List<Command> getCommands(String key) {
        List<Command> commands = new ArrayList<>();
        JSONArray members = this.getJSONArray(key);
        for (int i = 0; i < members.length(); i++) {
            commands.add(new Command(this.getJSONObject(i, members)));
        }
        return commands;
    }

    public String getMemberName() {
        return this.getString(this.getMemberType().getName());
    }

    public String getString(String key) {
        if (this.jsonObject.has(key)) {
            try {
                return this.jsonObject.getString(key);
            } catch (JSONException e) {
                throw new ParserException(ErrorMessage.INVALID_VALUE, key);
            }
        } else {
            throw new ParserException(ErrorMessage.KEY_NOT_FOUND, key);
        }
    }

    private JSONArray getJSONArray(String key) {
        if (this.jsonObject.has(key)) {
            try {
                return this.jsonObject.getJSONArray(key);
            } catch (JSONException e) {
                throw new ParserException(ErrorMessage.INVALID_VALUE, key);
            }
        } else {
            throw new ParserException(ErrorMessage.KEY_NOT_FOUND, key);
        }
    }

    private JSONObject getJSONObject(String key) {
        if (this.jsonObject.has(key)) {
            try {
                return this.jsonObject.getJSONObject(key);
            } catch (JSONException e) {
                throw new ParserException(ErrorMessage.INVALID_VALUE, key);
            }
        } else {
            throw new ParserException(ErrorMessage.KEY_NOT_FOUND, key);
        }
    }

    private JSONObject getJSONObject(int index, JSONArray jsonArray) {
        try {
            return jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            throw new ParserException(ErrorMessage.INVALID_ARRAY_VALUE);
        }
    }

}
