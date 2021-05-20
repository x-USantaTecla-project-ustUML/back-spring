package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.SessionPersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.CommandParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class CommandService implements ServiceVisitor {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private SessionPersistence sessionPersistence;
    private Stack<MemberService> stack;
    private Command command;

    @Autowired
    public CommandService(SessionPersistence sessionPersistence) {
        this.sessionPersistence = sessionPersistence;
    }

    public Member execute(Command command, String sessionId) {
        this.command = command;
        this.initialize(sessionId);
        CommandType commandType = this.command.getCommandType();
        if (commandType == CommandType.ADD) {
            this.stack.peek().add(this.command.getMember());
        } else if (commandType == CommandType.OPEN) {
            this.stack.peek().accept(this);
        } else if (commandType == CommandType.CLOSE) {
            if(this.stack.size() > 1) {
                this.stack.pop();
            } else {
                throw new CommandParserException(Error.CLOSE_NOT_ALLOWED);
            }
        }
        this.sessionPersistence.update(sessionId, this.getMembers());
        return this.getPeekMember();
    }

    private List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        for(MemberService memberService : this.stack) {
            members.add(memberService.getMember());
        }
        return members;
    }

    private void initialize(String sessionId) {
        List<Member> members = this.sessionPersistence.read(sessionId);
        this.stack = new Stack<>();
        for (Member member : members) {
            MemberAcceptor memberAcceptor = new MemberAcceptor();
            member.accept(memberAcceptor);
            this.push(memberAcceptor.get());
        }
    }

    private Member getPeekMember() {
        return this.stack.peek().getMember();
    }

    private void push(MemberService memberService) {
        this.beanFactory.autowireBean(memberService);
        this.stack.push(memberService);
    }

    @Override
    public void visit(PackageService packageService) {
        MemberAcceptor memberAcceptor = new MemberAcceptor();
        packageService.open(this.command).accept(memberAcceptor);
        this.push(memberAcceptor.get());
    }

    @Override
    public void visit(ClassService classService) {
        throw new CommandParserException(Error.OPEN_NOT_ALLOWED);
    }

}
