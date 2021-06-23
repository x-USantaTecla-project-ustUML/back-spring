package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ProjectBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.RelationBuilder;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.relations.*;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileRelationParserTest {

    private static final String TEST_FILES_PATH = "src/test/resources/reverseEngineering/";

    private FileRelationParser fileRelationParser;

    @BeforeEach
    void beforeEach() {
        this.fileRelationParser = new FileRelationParser();
    }

    @Test
    void testGivenFileParserWhenGetWithWrongPathThenThrowException() {
        assertThrows(ServiceException.class, () -> this.fileRelationParser.get(new ProjectBuilder().build(),
                new File("notFoundRoute")));
    }

    @SneakyThrows
    @Test
    void testGivenRelationParserWhenGetFileWithInheritanceRelationThenReturn() {
        String className = "InheritanceClass";
        File file = new File(FileRelationParserTest.TEST_FILES_PATH + className + ".java");
        Class classExtension = new ClassBuilder().name("Extension").build();
        Class classImplementation = new ClassBuilder().name("Implementation").build();
        Package pakage = new PackageBuilder().name("pakage").classes(classImplementation).build();
        Project project = new ProjectBuilder().pakage(pakage).classes(classExtension).build();
        List<Relation> expected = List.of(new Inheritance(classExtension, ""),
                new RelationBuilder().inheritance().target(classImplementation).route("pakage.Implementation").role("").build());
        assertThat(this.fileRelationParser.get(project, file), is(expected));
    }

    @SneakyThrows
    @Test
    void testGivenRelationParserWhenGetFileWithCompositionRelationThenReturn() {
        String className = "CompositionClass";
        File file = new File(FileRelationParserTest.TEST_FILES_PATH + className + ".java");
        Class classComposition = new ClassBuilder().name("Composition").build();
        Class classCompositionMethod = new ClassBuilder().name("CompositionMethod").build();
        Class classNotComposition = new ClassBuilder().name("NotComposition").build();
        Project project = new ProjectBuilder().classes(classComposition, classCompositionMethod, classNotComposition).build();
        List<Relation> expected = List.of(new Composition(classComposition, ""),
                new Composition(classCompositionMethod, ""), new Use(classNotComposition, ""));
        assertThat(this.fileRelationParser.get(project, file), is(expected));
    }

    @SneakyThrows
    @Test
    void testGivenRelationParserWhenGetFileWithAggregationRelationThenReturn() {
        String className = "AggregationClass";
        File file = new File(FileRelationParserTest.TEST_FILES_PATH + className + ".java");
        Class classAggregation = new ClassBuilder().name("Aggregation").build();
        Class classAggregationMethod = new ClassBuilder().name("AggregationMethod").build();
        Class classAggregationHashMap = new ClassBuilder().name("AggregationHashMap").build();
        Project project = new ProjectBuilder().classes(classAggregation, classAggregationMethod, classAggregationHashMap).build();
        List<Relation> expected = List.of(new Aggregation(classAggregation, "")
                , new Aggregation(classAggregationMethod, ""), new Aggregation(classAggregationHashMap, ""));
        assertThat(this.fileRelationParser.get(project, file), is(expected));
    }

    @SneakyThrows
    @Test
    void testGivenRelationParserWhenGetFileWithAssociationRelationThenReturn() {
        String className = "AssociationClass";
        File file = new File(FileRelationParserTest.TEST_FILES_PATH + className + ".java");
        Class classAssociation = new ClassBuilder().name("Association").build();
        Class classAssociationWithRoute = new ClassBuilder().name("AssociationWithRoute").build();
        Package pakage = new PackageBuilder().name("pakage").classes(classAssociationWithRoute).build();
        Project project = new ProjectBuilder().pakage(pakage).classes(classAssociation).build();
        List<Relation> expected = List.of(new Association(classAssociation, ""), new RelationBuilder()
                .association().target(classAssociationWithRoute).route("pakage.AssociationWithRoute").role("").build());
        List<Relation> actual = this.fileRelationParser.get(project, file);
        assertThat(actual, is(expected));
    }

    @SneakyThrows
    @Test
    void testGivenRelationParserWhenGetFileWithUseRelationThenReturn() {
        String className = "UseClass";
        File file = new File(FileRelationParserTest.TEST_FILES_PATH + className + ".java");
        Class adios = new ClassBuilder().name("Adios").build();
        Class pepe = new ClassBuilder().name("Pepe").build();
        Project project = new ProjectBuilder().classes(adios, pepe).build();
        List<Relation> expected = List.of(new Use(adios, ""), new Use(pepe, ""));
        assertThat(this.fileRelationParser.get(project, file), is(expected));
    }

    @SneakyThrows
    @Test
    void testGivenRelationParserWhenGetFileWithInvalidRouteThenThrowException() {
        String className = "InvalidRouteClass";
        File file = new File(FileRelationParserTest.TEST_FILES_PATH + className + ".java");
        assertThrows(ServiceException.class, () -> this.fileRelationParser.get(new ProjectBuilder().build(), file));
    }

    @SneakyThrows
    @Test
    void testGivenRelationParserWhenGetNonCompilingFileThenThrowException() {
        String className = "NonCompilingClass";
        File file = new File(FileRelationParserTest.TEST_FILES_PATH + className + ".java");
        assertThrows(ServiceException.class, () -> this.fileRelationParser.get(new ProjectBuilder().build(), file));
    }

}
