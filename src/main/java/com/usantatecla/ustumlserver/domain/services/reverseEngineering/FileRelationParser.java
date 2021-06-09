package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.metamodel.PropertyMetaModel;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class FileRelationParser extends VoidVisitorAdapter<Void> {

    private List<Relation> relations;
    private List<String> imports;
    private String pakageRoute;
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
        this.setPackageRoute(compilationUnit);
        this.visit(compilationUnit, null);
        return this.relations;
    }

    private void setPackageRoute(CompilationUnit compilationUnit) {
        Optional<PackageDeclaration> packageDeclaration = compilationUnit.getPackageDeclaration();
        if (packageDeclaration.isPresent()) {
            this.pakageRoute = packageDeclaration.get().getNameAsString();
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        super.visit(declaration, arg);
        this.addInheritanceRelations(declaration.getImplementedTypes());
        this.addInheritanceRelations(declaration.getExtendedTypes());
        for(VariableDeclarator variable:this.getVariables(declaration)){
            if(!this.isInConstructorParams(variable, declaration.getMetaModel().getConstructorParameters())){
                if(this.isInitialized(declaration)){
                    this.addCompositionRelation(variable);
                }
            }
        }
    }

    private void addCompositionRelation(VariableDeclarator variable) {
        String targetName = variable.getTypeAsString();
        Type type = variable.getType();
        if(type.isArrayType()){//TODO
            targetName = type.asArrayType().getComponentType().asString();
        }
        if(!type.isPrimitiveType()) {
            Member target = this.getTarget(targetName);
            if (target != null) {
                this.relations.add(new Composition(target, ""));
            }
        }
    }

    private boolean isInitialized(ClassOrInterfaceDeclaration declaration){
        for(ConstructorDeclaration constructorDeclaration: declaration.getConstructors()){
            for(Statement statement: constructorDeclaration.getBody().getStatements()){
                for(VariableDeclarator variable: this.getVariables(declaration)){
                    Pattern pattern = Pattern.compile(variable.getNameAsString()+"( )*=(?!=)");
                    if(pattern.matcher(statement.toString()).find()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isInConstructorParams(VariableDeclarator variable, List<PropertyMetaModel> parameters){
        for(PropertyMetaModel parameter:parameters){
            if(variable.getTypeAsString().equals(parameter.getType().getName())){
                return true;
            }
        }
        return false;
    }

    private List<VariableDeclarator> getVariables(ClassOrInterfaceDeclaration declaration) {
        List<VariableDeclarator> variables = new ArrayList<>();
        for (FieldDeclaration field : declaration.getFields()) {
            variables.addAll(field.getVariables());
        }
        return variables;
    }

    private void addInheritanceRelations(List<ClassOrInterfaceType> targetDeclarations){
        for (ClassOrInterfaceType declaration : targetDeclarations) {
            Member target = this.getTarget(declaration.getName().toString());
            if(target != null) {
                this.relations.add(new Inheritance(target, ""));
            }
        }
    }

    private Member getTarget(String targetName){
        Member target;
        if (targetName.contains("\\.")) {
            target = this.project.findRoute(targetName);
        } else {
            String _import = this.getImport(targetName);
            if (_import != null) {
                target = this.project.findRoute(_import);
            } else {
                Package pakage = (Package) this.project.findRoute(this.pakageRoute);
                target = pakage.find(targetName);
                if (target == null) {
                    target = this.getOutsideTarget(targetName);
                }
            }
        }
        return target;
    }

    private Member getOutsideTarget(String typeName) {
        for (String _import : this.imports) {
            Member member = this.project.findRoute(_import);
            if (member != null && member.isPackage()) {
                Member target = ((Package) member).find(typeName);
                if (target != null) {
                    return target;
                }
            }
        }
        return null;
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
