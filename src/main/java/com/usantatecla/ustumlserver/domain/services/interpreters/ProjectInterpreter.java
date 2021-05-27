package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.domain.services.parsers.ProjectParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandType;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjectInterpreter extends MemberInterpreter{

    @Autowired
    private PackagePersistence packagePersistence;

    public ProjectInterpreter(Member member) {
        super(member);
    }

    @Override
    public void add(Command command) {
        Project project = (Project) this.member;
        new ProjectParser().addMembers(project, command);
        this.packagePersistence.update(project);
    }

    public Member open(Command command) {
        String name = command.getString(CommandType.OPEN.getName());
        Member member = ((Project)this.member).find(name);
        if (member == null) {
            throw new ServiceException(ErrorMessage.MEMBER_NOT_FOUND, name);
        }
        return member;
    }

    @Override
    public void accept(InterpreterVisitor interpreterVisitor) {
        interpreterVisitor.visit(this);
    }
}
