package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

public class RepositoryParser {

    public Project get(File directory) {
        Project project = new Project(directory.getName(), new ArrayList<>());
        File mainDirectory = new File(directory.getPath() + this.getLocalPath());
        if(!mainDirectory.exists() || !mainDirectory.isDirectory()) {
            throw new ServiceException(ErrorMessage.DIRECTORY_NOT_FOUND, this.getLocalPath());
        }
        this.parseDirectory(project, mainDirectory);
        return project;
    }

    String getLocalPath() {
        return "/src/main/java/";
    }

    private void parseDirectory(Package pakage, File directory) {
        for(File file: Objects.requireNonNull(directory.listFiles())) {
            if(file.isDirectory()) {
                Package inside = new Package(file.getName(), new ArrayList<>());
                this.parseDirectory(inside, file);
                pakage.add(inside);
            } else {
                try {
                    pakage.add(new FileParser().get(file));
                } catch (FileNotFoundException e) {
                    throw new ServiceException(ErrorMessage.FILE_NOT_FOUND, file.getName());
                }
            }
        }
    }

}
