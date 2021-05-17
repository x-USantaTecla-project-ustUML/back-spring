package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Stack;

@Service
public class CommandService {

    private static final String STACK_KEY = "stack";

    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    private PackagePersistence packagePersistence;
    private Stack<MemberService> stack;

    @Autowired
    public CommandService(PackagePersistence packagePersistence) {
        this.packagePersistence = packagePersistence;
    }

    public Member execute(Command command) {
        this.initialize();
        if (command.getCommandType() == CommandType.ADD) {
            return this.stack.peek().add(command.getMember());
        }
        this.getSession().setAttribute(CommandService.STACK_KEY, this.stack);
        return null;
    }

    private void initialize() {
        this.stack = (Stack<MemberService>) this.getSession().getAttribute(CommandService.STACK_KEY);
        if (this.stack == null) {
            this.stack = new Stack<>();
            PackageService packageService = new PackageService(this.packagePersistence.read("name"));
            this.beanFactory.autowireBean(packageService);
            this.stack.push(packageService);
        }
    }

    private HttpSession getSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return request.getSession();
    }

}
