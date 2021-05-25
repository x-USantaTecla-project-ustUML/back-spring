package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.MemberVisitor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionPersistence extends MemberVisitor {
    List<Member> read(String sessionId);
    void update(String sessionId, List<Member> members);
    void delete(String sessionId);
}
