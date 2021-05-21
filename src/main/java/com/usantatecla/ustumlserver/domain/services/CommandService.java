package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.SessionPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class CommandService {

    private SessionPersistence sessionPersistence;
    private ServiceNavigator serviceNavigator;

    @Autowired
    public CommandService(SessionPersistence sessionPersistence, ServiceNavigator serviceNavigator) {
        this.sessionPersistence = sessionPersistence;
        this.serviceNavigator = serviceNavigator;
    }

    public Member execute(Command command, String sessionId) {
        this.serviceNavigator.initialize(this.sessionPersistence.read(sessionId));
        CommandType commandType = command.getCommandType();
        if (commandType == CommandType.ADD) {
            this.serviceNavigator.getActive().add(command.getMember());
        } else if (commandType == CommandType.OPEN) {
            this.serviceNavigator.open(command);
        } else if (commandType == CommandType.CLOSE) {
            this.serviceNavigator.close();
        }
        this.sessionPersistence.update(sessionId, this.serviceNavigator.getMembers());
        return this.serviceNavigator.getActiveMember();
    }

}
