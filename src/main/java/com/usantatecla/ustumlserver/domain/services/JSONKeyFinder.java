package com.usantatecla.ustumlserver.domain.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class JSONKeyFinder {

    private JSONObject jsonObject;
    private JSONArray jsonArray;

    JSONKeyFinder(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    JSONKeyFinder(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    JSONArray getJSONArray(String key) {
        assert this.jsonObject != null;

        if (this.jsonObject.has(key)) {
            try {
                return this.jsonObject.getJSONArray(key);
            } catch (JSONException e) {
                throw new CommandParserException(Error.INVALID_JSON.getDetail());
            }
        } else {
            throw new CommandParserException(Error.KEY_NOT_FOUND.getDetail());
        }
    }

    JSONObject getJSONObject(String key) {
        assert this.jsonObject != null;

        if (this.jsonObject.has(key)) {
            try {
                return this.jsonObject.getJSONObject(key);
            } catch (JSONException e) {
                throw new CommandParserException(Error.INVALID_JSON.getDetail());
            }
        } else {
            throw new CommandParserException(Error.KEY_NOT_FOUND.getDetail());
        }
    }

    String getString(String key) {
        assert this.jsonObject != null;

        if (this.jsonObject.has(key)) {
            try {
                return this.jsonObject.getString(key);
            } catch (JSONException e) {
                throw new CommandParserException(Error.INVALID_JSON.getDetail());
            }
        } else {
            throw new CommandParserException(Error.KEY_NOT_FOUND.getDetail());
        }
    }

    JSONObject getJSONObject(int index) {
        assert this.jsonArray != null;

        try {
            return this.jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            throw new CommandParserException(Error.INVALID_JSON.getDetail());
        }
    }

}
