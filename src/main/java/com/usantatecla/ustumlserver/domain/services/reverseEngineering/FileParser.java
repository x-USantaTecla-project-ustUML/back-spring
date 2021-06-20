package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.io.File;
import java.io.FileNotFoundException;

public class FileParser extends VoidVisitorAdapter<Void> {

    protected CompilationUnit getCompilationUnit(File file) {
        try {
            return StaticJavaParser.parse(file);
        } catch (FileNotFoundException e) {
            throw new ServiceException(ErrorMessage.FILE_NOT_FOUND, file.getName());
        } catch (ParseProblemException e) {
            throw new ServiceException(ErrorMessage.NON_COMPILING_FILE, file.getPath());
        }
    }

}
