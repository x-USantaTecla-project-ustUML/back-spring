package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class RepositoryParserTest {

    private static final String TEST_FILES_PATH = "src/test/java/" +
            "com/usantatecla/ustumlserver/domain/services/interpreters/filesToTest/";

    private RepositoryParser repositoryParser;

    @BeforeEach
    void beforeEach() {
        this.repositoryParser = spy(new RepositoryParser());
    }

    @Test
    void testGivenRepositoryParserWhenGetNonExistingDirectoryThenThrowException() {
        assertThrows(ServiceException.class, () -> this.repositoryParser.get(new File("notExistingPath")));
    }

    @Test
    void testGivenRepositoryParserWhenGetFileThenThrowException() {
        File file = new File(RepositoryParserTest.TEST_FILES_PATH + "EmptyClass.java");
        assertThrows(ServiceException.class, () -> this.repositoryParser.get(file));
    }

    /* // TODO To fix
    @Test
    void testGivenRepositoryParserWhenGetEmptyDirectoryThenReturn() {
        when(this.repositoryParser.getLocalPath()).thenReturn("");
        String projectName = "emptyPackage";
        File file = new File(RepositoryParserTest.TEST_FILES_PATH + projectName + "/");
        Project expected = new Project(projectName, new ArrayList<>());
        assertThat(this.repositoryParser.get(file), is(expected));
    }
    */
    @Test
    void testGivenRepositoryParserWhenGetDirectoryWithFewThingsThenReturn() {
        when(this.repositoryParser.getLocalPath()).thenReturn("");
        String projectName = "packageWithThings";
        File file = new File(RepositoryParserTest.TEST_FILES_PATH + projectName + "/");
        Project expected = new Project(projectName, List.of(
                new ClassBuilder().name("EmptyA").build(),
                new ClassBuilder().name("EmptyB").build()
        ));
        assertThat(this.repositoryParser.get(file), is(expected));
    }

    /* // TODO Test falla en SonarCloud por orden de los miembros en la lista dentro de Project
    @Test
    void testGivenRepositoryParserWhenGetDirectoryThenReturn() {
        when(this.repositoryParser.getLocalPath()).thenReturn("");
        // TODO Cambiar a los correspondientes tipos cuando esté implementado
        Package emptyPackage = new Package("emptyPackage", new ArrayList<>());
        Package packageWithThings = new Package("packageWithThings", List.of(
                new ClassBuilder().name("EmptyA").build(),
                new ClassBuilder().name("EmptyB").build()
        ));
        Class attributesClass = new ClassBuilder()._public()._abstract().name("AttributesClass")
                .attribute()._private()._static()._final().type("int").name("CONSTANT")
                .attribute()._private().type("int").name("privateInt")
                .attribute()._public().type("String").name("publicString")
                .attribute()._protected().type("Integer").name("protectedInteger")
                .attribute()._package().type("EmptyClass").name("packageEmptyClass")
                .build();
        Class emptyAnnotation = new Class("EmptyAnnotation", new ArrayList<>(), new ArrayList<>());
        Class emptyClass = new Class("EmptyClass", new ArrayList<>(), new ArrayList<>());
        Class emptyEnum = new Class("EmptyEnum", new ArrayList<>(), new ArrayList<>());
        Class emptyInterface = new Class("EmptyInterface", new ArrayList<>(), new ArrayList<>());
        Project expected = new Project("filesToTest", List.of(attributesClass, emptyAnnotation, emptyClass,
                emptyEnum, emptyInterface, emptyPackage, packageWithThings));
        assertThat(this.repositoryParser.get(new File(RepositoryParserTest.TEST_FILES_PATH)), is(expected));
    }
    */
}
