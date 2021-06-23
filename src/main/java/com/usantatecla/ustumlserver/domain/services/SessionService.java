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
        String email = this.getCurrentUserEmail();
        return this.sessionPersistence.read(sessionId, email);
    }

    String getCurrentUserEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void add(String sessionId, Member member) {
        this.sessionPersistence.add(sessionId, member);
    }

    public void delete(String sessionId) {
        this.sessionPersistence.delete(sessionId);
    }

    public void delete(String sessionId, Member member) {
        this.sessionPersistence.delete(sessionId, member);
    }

}
