package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionPersistence {
    List<Member> read(String sessionId, String email);
    void update(String sessionId, List<Member> members);
    void delete(String sessionId);
}
