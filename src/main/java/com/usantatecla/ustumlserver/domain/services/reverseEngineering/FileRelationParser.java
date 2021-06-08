package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.usantatecla.ustumlserver.domain.model.Inheritance;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class FileRelationParser extends VoidVisitorAdapter<Void> {

    private List<Relation> relations;
    private List<String> imports;
    private Project project;

    FileRelationParser() {
        this.relations = new ArrayList<>();
        this.imports = new ArrayList<>();
    }

    List<Relation> get(Project project, File file) {
        this.project = project;
        CompilationUnit compilationUnit;
        try {
            compilationUnit = StaticJavaParser.parse(file);
        } catch (FileNotFoundException e) {
            throw new ServiceException(ErrorMessage.FILE_NOT_FOUND, file.getName());
        }
        this.imports = compilationUnit.getImports().stream()
                .map(ImportDeclaration::getNameAsString)
                .collect(Collectors.toList());
        this.visit(compilationUnit, null);
        return this.relations;
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        super.visit(declaration, arg);
        for (ClassOrInterfaceType type : declaration.getImplementedTypes()) {
            String typeName = type.getName().toString();
            if (typeName.contains("\\.")) {
                this.createRelationFromRoute(typeName);
            } else {
                String _import = this.getImport(typeName);
                if (_import != null) {
                    this.createRelationFromRoute(_import);
                } else {
                    //TODO buscar en paquete
                }
            }
        }
    }

    private void createRelationFromRoute(String route) {
        Member target = this.project.findRoute(route);
        if (target != null) {
            this.relations.add(new Inheritance(target, ""));
        }
    }

    private String getImport(String name) {
        for (String _import : this.imports) {
            String[] splitImport = _import.split("\\.");
            if (splitImport[splitImport.length - 1].equals(name)) {
                return _import;
            }
        }
        return null;
    }

    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        super.visit(declaration, arg);
    }

}
