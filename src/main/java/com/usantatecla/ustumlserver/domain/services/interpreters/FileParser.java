package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.usantatecla.ustumlserver.domain.model.Class;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FileParser extends VoidVisitorAdapter<Void> {

    private Class clazz;

    public Class get(File file) throws FileNotFoundException {
        CompilationUnit compilationUnit = StaticJavaParser.parse(file);
        this.visit(compilationUnit, null);
        return this.clazz;
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        super.visit(declaration, arg);
        if (!declaration.isInterface()) {
            this.clazz = new Class(declaration.getNameAsString(), new ArrayList<>(), new ArrayList<>());
        } else {
            // TODO Crear interfaz cuando se implemente
            this.clazz = new Class(declaration.getNameAsString(), new ArrayList<>(), new ArrayList<>());
        }
    }

    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        super.visit(declaration, arg);
        // TODO Crear enums cuando se implemente
        this.clazz = new Class(declaration.getNameAsString(), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public void visit(AnnotationDeclaration declaration, Void arg) {
        super.visit(declaration, arg);
        // TODO Annotation como interfaz?
        this.clazz = new Class(declaration.getNameAsString(), new ArrayList<>(), new ArrayList<>());
    }
}
