package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;

public class ProjectParser extends PackageParser{

    @Override
    public ProjectParser copy() {
        return new ProjectParser();
    }

}
