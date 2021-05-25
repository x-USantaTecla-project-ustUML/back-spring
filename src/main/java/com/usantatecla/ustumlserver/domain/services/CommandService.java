package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.services.interpreters.InterpretersStack;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandService {

    private SessionService sessionService;
    private InterpretersStack interpretersStack;

    @Autowired
    public CommandService(SessionService sessionService, InterpretersStack interpretersStack) {
        this.sessionService = sessionService;
        this.interpretersStack = interpretersStack;
    }

    public Member execute(Command command, String sessionId) {
        this.interpretersStack.initialize(this.sessionService.read(sessionId));
        CommandType commandType = command.getCommandType();
        if (commandType == CommandType.ADD) {
            this.interpretersStack.getActiveService().add(command.getMember());
        } else if (commandType == CommandType.OPEN) {
            this.interpretersStack.open(command);
        } else if (commandType == CommandType.CLOSE) {
            this.interpretersStack.close();
        }
        this.sessionService.update(sessionId, this.interpretersStack.getMembers());
        return this.interpretersStack.getActiveMember();
    }

}
