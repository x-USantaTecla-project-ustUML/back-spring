package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.persistence.ClassPersistence;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class RepositoryParser {

    static final String PATH = "/src/main/java/";
    static final String JAVA_EXTENSION = ".java";

    private PackagePersistence packagePersistence;
    private ClassPersistence classPersistence;
    private Map<File, Class> classMap;

    @Autowired
    RepositoryParser(PackagePersistence packagePersistence, ClassPersistence classPersistence) {
        this.packagePersistence = packagePersistence;
        this.classPersistence = classPersistence;
        this.classMap = new HashMap<>();
    }

    Project get(Directory directory) {
        Project project = new Project(directory.getName(), new ArrayList<>());
        Directory mainDirectory = new Directory(directory.getPath() + RepositoryParser.PATH);
        this.parseDirectoryClasses(project, mainDirectory);
        this.parseDirectoryRelations(project);
        return (Project) this.packagePersistence.update(project);
    }

    private void parseDirectoryClasses(Package pakage, Directory directory) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                Package inside = this.packagePersistence.update(new Package(file.getName(), new ArrayList<>()));
                this.parseDirectoryClasses(inside, new Directory(file));
                pakage.add(inside);
            } else {
                if (file.getName().contains(RepositoryParser.JAVA_EXTENSION)) {
                    Class clazz = this.classPersistence.update(new FileClassParser().get(file));
                    this.classMap.put(file, clazz);
                    pakage.add(clazz);
                }
            }
        }
    }

    private void parseDirectoryRelations(Project project) {
        for (Map.Entry<File, Class> entry : this.classMap.entrySet()) {
            entry.getValue().setRelations(new FileRelationParser().get(project, entry.getKey()));
        }
    }

}
