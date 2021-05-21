package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.services.parsers.CommandParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Component
class ServiceNavigator implements ServiceVisitor {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private Stack<MemberService> stack;
    private Command command;

    void initialize(List<Member> members) {
        this.stack = new Stack<>();
        for (Member member : members) {
            this.push(member);
        }
    }

    private void push(Member member) {
        MemberService memberService = new ServiceCreator().create(member);
        this.beanFactory.autowireBean(memberService);
        this.stack.push(memberService);
    }

    void open(Command command) {
        this.command = command;
        this.stack.peek().accept(this);
    }

    void close() {
        if (this.stack.size() > 1) {
            this.stack.pop();
        } else {
            throw new CommandParserException(Error.CLOSE_NOT_ALLOWED);
        }
    }

    List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        for (MemberService memberService : this.stack) {
            members.add(memberService.getMember());
        }
        return members;
    }

    Member getActiveMember() {
        return this.getActive().getMember();
    }

    MemberService getActive() {
        return this.stack.peek();
    }

    @Override
    public void visit(PackageService packageService) {
        this.push(packageService.open(this.command));
    }

    @Override
    public void visit(ClassService classService) {
        throw new CommandParserException(Error.OPEN_NOT_ALLOWED);
    }


}
