package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.SessionPersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.CommandParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    private SessionPersistence sessionPersistence;

    @Autowired
    public SessionService(SessionPersistence sessionPersistence) {
        this.sessionPersistence = sessionPersistence;
    }

    public List<Member> read(String sessionId) {
        return this.sessionPersistence.read(sessionId);
    }

    public void update(String sessionId, List<Member> members) {
        this.sessionPersistence.update(sessionId, members);
    }

    public void delete(String sessionId) {
        this.sessionPersistence.delete(sessionId);
    }

}
