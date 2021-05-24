package com.usantatecla.ustumlserver.configuration;

import com.usantatecla.ustumlserver.domain.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionListener implements ApplicationListener<SessionDestroyedEvent> {

    private SessionService sessionService;

    @Autowired
    public SessionListener(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        this.sessionService.delete(event.getId());
    }

}