package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.services.MemberService;

import java.util.List;
import java.util.Stack;

public interface SessionPersistence {
    List<Member> read(String sessionId);

    void update(String sessionId, List<Member> members);
}
