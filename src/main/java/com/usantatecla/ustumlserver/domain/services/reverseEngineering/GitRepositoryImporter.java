package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.usantatecla.ustumlserver.domain.model.Project;

public class GitRepositoryImporter {

    private RepositoryParser repositoryParser;
    private Directory directory;

    public GitRepositoryImporter() {
        this.repositoryParser = new RepositoryParser();
    }

    public Project importMembers(String url, String email) {
        this.directory = new GitCloner().clone(url, email);
        return this.repositoryParser.getProject(this.directory);
    }

    public Project importRelations() {
        this.directory.delete();
        return this.repositoryParser.getProjectWithRelations();
    }

}
