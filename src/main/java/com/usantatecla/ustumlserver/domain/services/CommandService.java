package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Stack;

@Service
public class CommandService {

    private static final String STACK = "stack";
    private Stack<MemberService> stack;
    @Autowired
    private PackagePersistence packagePersistence;

    public CommandService() {
        this.stack = (Stack<MemberService>) this.getSession().getAttribute(CommandService.STACK);
        if (this.stack == null) {
            this.stack = new Stack<>();
            this.stack.push(new PackageService(this.packagePersistence.read("name")));
        }
    }

    public Member execute(Command command) {
        if (command.getCommandType() == CommandType.ADD) {
            return this.stack.peek().add(command.getMember());
        }
        this.getSession().setAttribute(CommandService.STACK, this.stack);
        return null;
    }

    private HttpSession getSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return request.getSession();
    }

}
