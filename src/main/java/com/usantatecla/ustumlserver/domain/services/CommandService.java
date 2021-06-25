package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.services.interpreters.InterpretersStack;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

@Service
public class CommandService {

    private AutowireCapableBeanFactory beanFactory;
    private SessionService sessionService;

    @Autowired
    public CommandService(AutowireCapableBeanFactory beanFactory, SessionService sessionService) {
        this.beanFactory = beanFactory;
        this.sessionService = sessionService;
    }

    public Member execute(Command command, String sessionId) {
        InterpretersStack interpretersStack = this.createInterpretersStack(sessionId);
        CommandType commandType = command.getCommandType();
        if (commandType == CommandType.ADD) {
            interpretersStack.getPeekInterpreter().add(command.getMember());
        } else if (commandType == CommandType.MODIFY) {
            interpretersStack.getPeekInterpreter().modify(command.getMember());
        } else if (commandType == CommandType.DELETE) {
            interpretersStack.getPeekInterpreter().delete(command.getMember());
        } else if (commandType == CommandType.IMPORT) {
            interpretersStack.getPeekInterpreter()._import(command);
        } else if (commandType == CommandType.OPEN) {
            interpretersStack.open(command);
        } else if (commandType == CommandType.CLOSE) {
            interpretersStack.close();
        }
        return interpretersStack.getPeekMember();
    }

    private InterpretersStack createInterpretersStack(String sessionId) {
        InterpretersStack interpretersStack = new InterpretersStack(this.sessionService, this.beanFactory);
        interpretersStack.initialize(sessionId);
        return interpretersStack;
    }

    public Member getContext(String sessionId) {
        InterpretersStack interpretersStack = this.createInterpretersStack(sessionId);
        return interpretersStack.getPeekMember();
    }

}
