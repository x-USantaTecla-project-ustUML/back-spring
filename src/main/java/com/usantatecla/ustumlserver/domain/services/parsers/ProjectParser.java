package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Project;

public class ProjectParser extends PackageParser {

    public ProjectParser(Account account) {
        super(account);
    }

    @Override
    public Project createPackage() {
        return new Project(this.name, this.members);
    }

}
