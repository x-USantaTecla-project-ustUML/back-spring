package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;

import java.io.File;
import java.util.ArrayList;

public class RepositoryParser {

    static final String PATH = "/src/main/java/";
    static final String JAVA_EXTENSION = ".java";

    public Project get(Directory directory) {
        Project project = new Project(directory.getName(), new ArrayList<>());
        Directory mainDirectory = new Directory(directory.getPath() + RepositoryParser.PATH);
        this.parseDirectory(project, mainDirectory);
        return project;
    }

    private void parseDirectory(Package pakage, Directory directory) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                Package inside = new Package(file.getName(), new ArrayList<>());
                this.parseDirectory(inside, new Directory(file));
                pakage.add(inside);
            } else {
                if (file.getName().contains(RepositoryParser.JAVA_EXTENSION)) {
                    pakage.add(new FileParser().get(file));
                }
            }
        }
    }

}
