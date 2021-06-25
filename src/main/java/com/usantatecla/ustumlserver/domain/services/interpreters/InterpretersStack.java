package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Interface;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.UseCase;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.domain.services.SessionService;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class InterpretersStack {

    private SessionService sessionService;
    private AutowireCapableBeanFactory beanFactory;

    private String sessionId;
    private Deque<MemberInterpreter> stack;

    public InterpretersStack(SessionService sessionService, AutowireCapableBeanFactory beanFactory) {
        this.sessionService = sessionService;
        this.beanFactory = beanFactory;
    }

    public void initialize(String sessionId) {
        this.sessionId = sessionId;
        List<Member> members = this.sessionService.read(sessionId);
        this.stack = new ArrayDeque<>();
        for (Member member : members) {
            this.push(member);
        }
    }

    private void push(Member member) {
        MemberInterpreter memberInterpreter = new InterpreterCreator().create(this.getAccount(), member);
        this.beanFactory.autowireBean(memberInterpreter);
        this.stack.push(memberInterpreter);
    }

    public void open(Command command) {
        Member member = this.getPeekInterpreter().open(command);
        this.push(member);
        this.sessionService.add(this.sessionId, member);
    }

    public void close() {
        if (this.stack.size() > 1) {
            this.sessionService.delete(this.sessionId, this.stack.pop().getMember());
        } else {
            throw new ServiceException(ErrorMessage.CLOSE_NOT_ALLOWED);
        }
    }

    public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        for (MemberInterpreter memberInterpreter : this.stack) {
            members.add(memberInterpreter.getMember());
        }
        return members;
    }

    public Member getPeekMember() {
        return this.getPeekInterpreter().getMember();
    }

    public MemberInterpreter getPeekInterpreter() {
        return this.stack.peek();
    }

    public Account getAccount() {
        if (this.stack.isEmpty()) return null;
        return (Account) this.stack.getLast().getMember();
    }

    private class InterpreterCreator implements MemberVisitor {

        private Account account;
        private MemberInterpreter memberInterpreter;

        MemberInterpreter create(Account account, Member member) {
            this.account = account;
            member.accept(this);
            return this.memberInterpreter;
        }

        @Override
        public void visit(Account account) {
            this.memberInterpreter = new AccountInterpreter(account, account);
        }

        @Override
        public void visit(Package pakage) {
            this.memberInterpreter = new PackageInterpreter(this.account, pakage);
        }

        @Override
        public void visit(Project project) {
            this.memberInterpreter = new PackageInterpreter(this.account, project);
        }

        @Override
        public void visit(Class clazz) {
            this.memberInterpreter = new ClassInterpreter(this.account, clazz);
        }

        @Override
        public void visit(Interface _interface) {
            this.memberInterpreter = new ClassInterpreter(this.account, _interface);
        }

        @Override
        public void visit(Enum _enum) {
            this.memberInterpreter = new EnumInterpreter(this.account, _enum);
        }

        @Override
        public void visit(Actor actor) {
            this.memberInterpreter = new MemberInterpreter(this.account, actor);
        }

        @Override
        public void visit(UseCase useCase) {
            this.memberInterpreter = new MemberInterpreter(this.account, useCase);
        }

    }
}
