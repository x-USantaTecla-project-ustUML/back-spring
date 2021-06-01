package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Modifier;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RepositoryParser {

    private static String PATH = "/src/main/java/";

    public Project get(File directory) {
        Project project = new Project(directory.getName(), new ArrayList<>());
        File mainDirectory = new File(directory.getPath() + RepositoryParser.PATH);
        System.out.println(mainDirectory.getPath());
        if(!mainDirectory.exists() || !mainDirectory.isDirectory()) {
            throw new ServiceException(ErrorMessage.DIRECTORY_NOT_FOUND, RepositoryParser.PATH);
        }
        this.parseDirectory(project, mainDirectory);
        return project;
    }

    public void parseDirectory(Package pakage, File directory) {
        for(File file: Objects.requireNonNull(directory.listFiles())) {
            if(file.isDirectory()) {
                Package inside = new Package(file.getName(), new ArrayList<>());
                this.parseDirectory(inside, file);
                pakage.add(inside);
            } else {
                // TODO Visit...
                pakage.add(new Class(file.getName(), List.of(Modifier.PACKAGE), new ArrayList<>()));
            }
        }
    }

}
