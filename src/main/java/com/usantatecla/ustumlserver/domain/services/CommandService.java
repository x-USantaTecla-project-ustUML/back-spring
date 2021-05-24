package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandService {

    private SessionService sessionService;
    private ServiceNavigator serviceNavigator;

    @Autowired
    public CommandService(SessionService sessionService, ServiceNavigator serviceNavigator) {
        this.sessionService = sessionService;
        this.serviceNavigator = serviceNavigator;
    }

    public Member execute(Command command, String sessionId) {
        this.serviceNavigator.initialize(this.sessionService.read(sessionId));
        CommandType commandType = command.getCommandType();
        if (commandType == CommandType.ADD) {
            this.serviceNavigator.getActive().add(command.getMember());
        } else if (commandType == CommandType.OPEN) {
            this.serviceNavigator.open(command);
        } else if (commandType == CommandType.CLOSE) {
            this.serviceNavigator.close();
        }
        this.sessionService.update(sessionId, this.serviceNavigator.getMembers());
        return this.serviceNavigator.getActiveMember();
    }

}
