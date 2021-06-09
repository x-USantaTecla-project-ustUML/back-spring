package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.usantatecla.ustumlserver.domain.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitRepositoryImporter {

    private RepositoryParser repositoryParser;

    @Autowired
    public GitRepositoryImporter(RepositoryParser repositoryParser) {
        this.repositoryParser = repositoryParser;
    }

    public Project _import(String url, String email) {
        Directory directory = new GitCloner().clone(url, email);
        Project project = this.repositoryParser.get(directory);
        directory.delete();
        return project;
    }

}
