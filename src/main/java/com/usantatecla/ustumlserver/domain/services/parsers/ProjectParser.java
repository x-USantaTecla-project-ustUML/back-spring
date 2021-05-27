package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;

public class ProjectParser extends PackageParser{

    @Override
    public Project get(Command command) {
        this.parseName(command);
        Project project = new Project(this.name, this.members);
        if (command.has(PackageParser.MEMBERS_KEY)) {
            this.addMembers(project, command);
        }
        return project;
    }

    @Override
    public ProjectParser copy() {
        return new ProjectParser();
    }

}
