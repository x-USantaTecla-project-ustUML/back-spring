package com.usantatecla.ustumlserver.configuration;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class LogoutListener implements ApplicationListener<SessionDestroyedEvent> {

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        System.out.println(sessionId);
    }

}