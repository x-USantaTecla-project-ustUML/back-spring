package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class AddService extends CommandParser {

    static final String MEMBERS = "members";

    private List<Member> members;

    AddService() {
        this.members = new ArrayList<>();
        this.member = new Package("name", this.members);
    }

    @Override
    Error parse(JSONObject json) {
        if (json.has(AddService.MEMBERS)) {
            JSONArray jsonArray = new JSONKeyFinder(json).getJSONArray(AddService.MEMBERS);
            JSONObject jsonObject;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = new JSONKeyFinder(jsonArray).getJSONObject(i);
                if (json.keys().hasNext()) {
                    String member = jsonObject.keys().next().toString();
                    MemberType memberType = MemberType.get(member);
                    if (memberType.isNull()) {
                        return Error.MEMBER_NOT_FOUND;
                    }
                    this.members.add(memberType.create().get(jsonObject));
                }
            }
        }
        return Error.NULL;
    }

}
