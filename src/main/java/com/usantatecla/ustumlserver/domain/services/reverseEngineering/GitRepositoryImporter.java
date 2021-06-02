package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.usantatecla.ustumlserver.domain.model.Project;

public class GitRepositoryImporter {

    private GitCloner gitCloner;

    public GitRepositoryImporter() {
        this.gitCloner = new GitCloner();
    }

    public Project _import(String url, String email) {
        Directory directory = this.gitCloner.clone(url, email);
        Project project = new RepositoryParser().get(directory);
        directory.delete();
        return project;

    }

}
