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
                return null;//TODO throws exception
            }
        } else {
            return null;//TODO throws exception
        }
    }

    JSONObject getJSONObject(String key) {
        assert this.jsonObject != null;

        if (this.jsonObject.has(key)) {
            try {
                return this.jsonObject.getJSONObject(key);
            } catch (JSONException e) {
                return null;//TODO throws exception
            }
        } else {
            return null;//TODO throws exception
        }
    }

    String getString(String key) {
        assert this.jsonObject != null;

        if (this.jsonObject.has(key)) {
            try {
                return this.jsonObject.getString(key);
            } catch (JSONException e) {
                return null;//TODO throws exception
            }
        } else {
            return null;//TODO throws exception
        }
    }

    JSONObject getJSONObject(int index) {
        assert this.jsonArray != null;

        try {
            return this.jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            return null;//TODO throws exception
        }
    }

}
