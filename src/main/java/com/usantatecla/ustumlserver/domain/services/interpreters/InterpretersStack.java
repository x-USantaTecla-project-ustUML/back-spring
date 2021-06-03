package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
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
        this.push(this.getPeekInterpreter().open(command));
    }

    public void close() {
        if (this.stack.size() > 1) {
            this.stack.pop();
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

    public Member getAccount() {
        return this.stack.firstElement().getMember();
    }

    private class InterpreterCreator implements MemberVisitor {

        private MemberInterpreter memberInterpreter;

        MemberInterpreter create(Member member) {
            member.accept(this);
            return this.memberInterpreter;
        }

        @Override
        public void visit(Account account) {
            this.memberInterpreter = new AccountInterpreter(account);
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
