package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.SessionPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    private SessionPersistence sessionPersistence;
    private TokenManager tokenManager;

    @Autowired
    public SessionService(SessionPersistence sessionPersistence, TokenManager tokenManager) {
        this.sessionPersistence = sessionPersistence;
        this.tokenManager = tokenManager;
    }

    public List<Member> read(String sessionId, String token) {
        String extractedToken = tokenManager.extractToken(token);
        return this.sessionPersistence.read(sessionId, tokenManager.user(extractedToken));
    }

    public void update(String sessionId, List<Member> members) {
        this.sessionPersistence.update(sessionId, members);
    }

    public void delete(String sessionId) {
        this.sessionPersistence.delete(sessionId);
    }

}
