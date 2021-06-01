package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.usantatecla.ustumlserver.domain.model.Attribute;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Modifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
        List<Modifier> modifiers = this.parseModifiers(declaration.getModifiers());
        List<Attribute> attributes = this.parseFields(declaration.getFields());
        if (!declaration.isInterface()) {
            this.clazz = new Class(declaration.getNameAsString(), modifiers, attributes);
        } else {
            // TODO Crear interfaz cuando se implemente
            this.clazz = new Class(declaration.getNameAsString(), modifiers, attributes);
        }
    }


    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        super.visit(declaration, arg);
        List<Modifier> modifiers = this.parseModifiers(declaration.getModifiers());
        List<Attribute> attributes = this.parseFields(declaration.getFields());
        // TODO Crear enums cuando se implemente
        this.clazz = new Class(declaration.getNameAsString(), modifiers, attributes);
    }

    @Override
    public void visit(AnnotationDeclaration declaration, Void arg) {
        super.visit(declaration, arg);
        List<Modifier> modifiers = this.parseModifiers(declaration.getModifiers());
        List<Attribute> attributes = this.parseFields(declaration.getFields());
        // TODO Annotation como interfaz?
        this.clazz = new Class(declaration.getNameAsString(), modifiers, attributes);
    }

    private List<Attribute> parseFields(List<FieldDeclaration> fields) {
        List<Attribute> attributes = new ArrayList<>();
        for (FieldDeclaration field : fields) {
            List<Modifier> modifiers = this.parseModifiers(field.getModifiers());
            for (VariableDeclarator variable : field.getVariables()) {
                String name = variable.getNameAsString();
                String type = variable.getTypeAsString();
                attributes.add(new Attribute(name, type, modifiers));
            }
        }
        return attributes;
    }

    private List<Modifier> parseModifiers(List<com.github.javaparser.ast.Modifier> declarationModifiers) {
        List<Modifier> modifiers = new ArrayList<>();
        for (com.github.javaparser.ast.Modifier modifier : declarationModifiers) {
            switch (modifier.getKeyword()) {
                case PUBLIC:
                    modifiers.add(Modifier.PUBLIC);
                    break;
                case DEFAULT:
                    modifiers.add(Modifier.PACKAGE);
                    break;
                case PRIVATE:
                    modifiers.add(Modifier.PRIVATE);
                    break;
                case PROTECTED:
                    modifiers.add(Modifier.PROTECTED);
                    break;
                case FINAL:
                    modifiers.add(Modifier.FINAL);
                    break;
                case STATIC:
                    modifiers.add(Modifier.STATIC);
                    break;
                case ABSTRACT:
                    modifiers.add(Modifier.ABSTRACT);
                    break;
            }
        }
        return modifiers;
    }

}
