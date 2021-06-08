package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RepositoryParser {

    static final String PATH = "/src/main/java/";
    static final String JAVA_EXTENSION = ".java";

    private Project project;
    private Map<File, Class> classMap;

    RepositoryParser() {
        this.classMap = new HashMap<>();
    }

    Project getProject(Directory directory) {
        this.project = new Project(directory.getName(), new ArrayList<>());
        Directory mainDirectory = new Directory(directory.getPath() + RepositoryParser.PATH);
        this.parseDirectoryClasses(this.project, mainDirectory);
        return this.project;
    }

    private void parseDirectoryClasses(Package pakage, Directory directory) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                Package inside = new Package(file.getName(), new ArrayList<>());
                this.parseDirectoryClasses(inside, new Directory(file));
                pakage.add(inside);
            } else {
                if (file.getName().contains(RepositoryParser.JAVA_EXTENSION)) {
                    Class clazz = new FileClassParser().get(file);
                    this.classMap.put(file, clazz);
                    pakage.add(clazz);
                }
            }
        }
    }

    Project getProjectWithRelations() {
        for (Map.Entry<File, Class> entry : this.classMap.entrySet()) {
            entry.getValue().setRelations(new FileRelationParser().get(this.project, entry.getKey()));
        }
        return this.project;
    }

}
