package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

public class GitRepositoryImporter {

    private GitCloner gitCloner;

    public GitRepositoryImporter() {
        this.gitCloner = new GitCloner();
    }

    public Project _import(String url, String email) {
        File directory = this.gitCloner.clone(url, email);
        String projectName = directory.getName();
        // TODO Llamar parseador de AST - Modelo y devuelve Project
        try {
            FileUtils.deleteDirectory(directory);
        } catch (IOException e) {
            throw new ServiceException(ErrorMessage.CLONE_ERROR, e.getMessage());
        }
        return new Project(projectName, new ArrayList<>());

    }

}
