package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Component
public class InterpretersStack {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private Stack<MemberInterpreter> stack;
    private Command command;

    public void initialize(List<Member> members) {
        this.stack = new Stack<>();
        for (Member member : members) {
            this.push(member);
        }
    }

    private void push(Member member) {
        MemberInterpreter memberInterpreter = new InterpreterCreator().create(member);
        this.beanFactory.autowireBean(memberInterpreter);
        this.stack.push(memberInterpreter);
    }

    public void open(Command command) {
        this.command = command;
        new InterpreterStacker(this).push(this.stack.peek());
    }

    public void close() {
        if (this.stack.size() > 1) {
            this.stack.pop();
        } else {
            throw new ServiceException(ErrorMessage.CLOSE_NOT_ALLOWED);
        }
    }

    private Command getCommand() {
        return this.command;
    }

    public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        for (MemberInterpreter memberInterpreter : this.stack) {
            members.add(memberInterpreter.getMember());
        }
        return members;
    }

    public Member getActiveMember() {
        return this.getActiveService().getMember();
    }

    public MemberInterpreter getActiveService() {
        return this.stack.peek();
    }

    class InterpreterStacker implements InterpreterVisitor {

        private InterpretersStack interpretersStack;

        InterpreterStacker(InterpretersStack interpretersStack) {
            this.interpretersStack = interpretersStack;
        }

        void push(MemberInterpreter memberInterpreter) {
            memberInterpreter.accept(this);
        }

        @Override
        public void visit(ProjectInterpreter projectInterpreter) {
            this.interpretersStack.push(projectInterpreter.open(this.interpretersStack.getCommand()));
        }

        @Override
        public void visit(PackageInterpreter packageInterpreter) {
            this.interpretersStack.push(packageInterpreter.open(this.interpretersStack.getCommand()));
        }

        @Override
        public void visit(ClassInterpreter classInterpreter) {
            throw new ServiceException(ErrorMessage.OPEN_NOT_ALLOWED);
        }

    }

    class InterpreterCreator implements MemberVisitor {

        private MemberInterpreter memberInterpreter;

        MemberInterpreter create(Member member) {
            member.accept(this);
            return this.memberInterpreter;
        }

        @Override
        public void visit(Project project) {
            this.memberInterpreter = new ProjectInterpreter(project);
        }

        @Override
        public void visit(Package pakage) {
            this.memberInterpreter = new PackageInterpreter(pakage);
        }

        @Override
        public void visit(Class clazz) {
            this.memberInterpreter = new ClassInterpreter(clazz);
        }

    }
}