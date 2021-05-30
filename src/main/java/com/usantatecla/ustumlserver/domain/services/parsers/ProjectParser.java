package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Project;

public class ProjectParser extends PackageParser {

    @Override
    public Project createPackage() {
        return new Project(this.name, this.members);
    }

}
