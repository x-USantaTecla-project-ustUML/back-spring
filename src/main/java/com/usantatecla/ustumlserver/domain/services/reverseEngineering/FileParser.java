package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Parameter;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileParser extends VoidVisitorAdapter<Void> {

    private List<String> imports;
    private Class clazz;

    public Class get(File file) {
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
        return this.clazz;
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        super.visit(declaration, arg);
        List<Modifier> modifiers = this.parseModifiers(declaration.getModifiers());
        List<Attribute> attributes = this.parseFields(declaration.getFields());
        List<Method> methods = this.parseMethods(declaration.getMethods());
        List<Relation> relations = this.parseRelations(declaration);
        if (!declaration.isInterface()) {
            this.clazz = new Class(declaration.getNameAsString(), modifiers, attributes);
        } else {
            // TODO Crear interfaz cuando se implemente
            this.clazz = new Class(declaration.getNameAsString(), modifiers, attributes);
        }
        this.clazz.setMethods(methods);
        this.clazz.setRelations(relations);
    }

    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        super.visit(declaration, arg);
        List<Modifier> modifiers = this.parseModifiers(declaration.getModifiers());
        List<Attribute> attributes = this.parseFields(declaration.getFields());
        List<Method> methods = this.parseMethods(declaration.getMethods());
        // TODO Crear enums cuando se implemente
        this.clazz = new Class(declaration.getNameAsString(), modifiers, attributes);
        this.clazz.setMethods(methods);
    }

    @Override
    public void visit(AnnotationDeclaration declaration, Void arg) {
        super.visit(declaration, arg);
        List<Modifier> modifiers = this.parseModifiers(declaration.getModifiers());
        List<Attribute> attributes = this.parseFields(declaration.getFields());
        List<Method> methods = this.parseMethods(declaration.getMethods());
        // TODO Annotation como interfaz?
        this.clazz = new Class(declaration.getNameAsString(), modifiers, attributes);
        this.clazz.setMethods(methods);
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

    private List<Method> parseMethods(List<MethodDeclaration> methodDeclarations) {
        List<Method> methods = new ArrayList<>();
        for (MethodDeclaration methodDeclaration : methodDeclarations) {
            List<Modifier> modifiers = this.parseModifiers(methodDeclaration.getModifiers());
            String name = methodDeclaration.getNameAsString();
            String type = methodDeclaration.getTypeAsString();
            List<Parameter> parameters = new ArrayList<>();
            for (com.github.javaparser.ast.body.Parameter parameter : methodDeclaration.getParameters()) {
                parameters.add(new Parameter(parameter.getNameAsString(), parameter.getTypeAsString()));
            }
            Method method = new Method(name, type, modifiers);
            method.setParameters(parameters);
            methods.add(method);
        }
        return methods;
    }

    private List<Relation> parseRelations(ClassOrInterfaceDeclaration declaration) {
        List<Relation> relations = new ArrayList<>();

        for(ClassOrInterfaceType classOrInterfaceType: declaration.getImplementedTypes()) {
            for(String importDeclaration: this.imports) {
                String[] route = importDeclaration.split("\\.");
                System.out.println(classOrInterfaceType.getNameAsString().equals(route[route.length - 1]));
            }
            Inheritance inheritance = new Inheritance();
            // System.out.println(classOrInterfaceType.getNameAsString());
        }
        return relations;
    }

    private List<Relation> parseRelations(EnumDeclaration declaration) {
        return new ArrayList<>();
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
