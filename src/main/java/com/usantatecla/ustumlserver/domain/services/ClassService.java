package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;

class ClassService extends MemberService {

    ClassService(Member member) {
        super(member);
    }

    @Override
    public Class add(Command command) {
        return null;
    }

}
