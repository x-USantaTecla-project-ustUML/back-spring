package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;

abstract class MemberService {

    abstract Member add(Command command);

    public abstract MemberService clone();

}
