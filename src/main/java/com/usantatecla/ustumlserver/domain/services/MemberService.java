package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import org.springframework.stereotype.Service;

@Service
abstract class MemberService {

    protected Member member;

    public MemberService(Member member) {
        this.member = member;
    }

    abstract Member add(Command command);

}
