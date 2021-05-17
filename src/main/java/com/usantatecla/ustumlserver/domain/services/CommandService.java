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

    private Stack<MemberService> stack;
    @Autowired
    private PackagePersistence packagePersistence;

    public CommandService() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        this.stack = (Stack<MemberService>) session.getAttribute("stack");
        if (this.stack == null) {
            this.stack = new Stack<>();
            this.stack.push(new PackageService(this.packagePersistence.read("name")));
        }
    }

    public Member execute(Command command) {
        if (command.getCommandType() == CommandType.ADD) {
            return this.stack.peek().add(command.getMember());
        }
        return null;
    }

}
