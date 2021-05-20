package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.CommandParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Stack;

@Service
public class CommandService implements ServiceVisitor {

    private static final String STACK_KEY = "stack";

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private PackagePersistence packagePersistence;
    private Stack<MemberService> stack;
    private Command command;

    @Autowired
    public CommandService(PackagePersistence packagePersistence) {
        this.packagePersistence = packagePersistence;
    }

    public Member execute(Command command, HttpSession session) {
        this.command = command;
        this.initialize((Stack<MemberService>) session.getAttribute("stack"));
        CommandType commandType = this.command.getCommandType();
        if (commandType == CommandType.ADD) {
            this.stack.peek().add(this.command.getMember());
        } else if(commandType == CommandType.OPEN) {
            this.stack.peek().accept(this);
        } else if(commandType == CommandType.CLOSE) {
            this.stack.pop();
        }
        session.setAttribute("stack", this.stack);
        return this.getPeekMember();
    }

    private Member getPeekMember() {
        return this.stack.peek().getMember();
    }

    private void initialize(Stack<MemberService> stack) {
        this.stack = stack;
        if (this.stack == null) {
            this.stack = new Stack<>();
            this.push(new PackageService(this.packagePersistence.read("name")));
        }
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
