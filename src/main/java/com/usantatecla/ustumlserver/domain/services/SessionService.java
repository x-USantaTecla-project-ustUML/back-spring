package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.SessionPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.sessionPersistence.read(sessionId, email);
    }

    public void update(String sessionId, List<Member> members) {
        this.sessionPersistence.update(sessionId, members);
    }

    public void delete(String sessionId) {
        this.sessionPersistence.delete(sessionId);
    }

}
