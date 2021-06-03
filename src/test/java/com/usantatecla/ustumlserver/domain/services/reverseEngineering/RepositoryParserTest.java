package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RepositoryParserTest {

    private static final String TEST_FILES_PATH = "src/test/resources/reverseEngineering/";

    private RepositoryParser repositoryParser;

    @BeforeEach
    void beforeEach() {
        this.repositoryParser = new RepositoryParser();
    }

    @Test
    void testGivenRepositoryParserWhenGetNonExistingDirectoryThenThrowException() {
        assertThrows(ServiceException.class, () -> this.repositoryParser.get(new Directory("notExistingPath")));
    }

    @Test
    void testGivenRepositoryParserWhenGetEmptyDirectoryThenReturn() {
        String projectName = "emptyRepository";
        Directory directory = new Directory(RepositoryParserTest.TEST_FILES_PATH + projectName);
        Project expected = new Project(projectName, new ArrayList<>());
        assertThat(this.repositoryParser.get(directory), is(expected));
    }

    @Test
    void testGivenRepositoryParserWhenGetDirectoryThenReturn() {
        String projectName = "repository";
        Directory directory = new Directory(RepositoryParserTest.TEST_FILES_PATH + projectName + "/");
        Project expected = new Project(projectName, List.of(
                new ClassBuilder()._abstract().name("ClassA")
                        .attribute()._protected().type("String").name("attribute")
                        .method()._public()._abstract().type("int").name("method")
                        .build(),
                new PackageBuilder().name("packageA")
                        .classes(new ClassBuilder()._public().name("ClassB")
                                .attribute()._private().type("char").name("attribute")
                                .method()._public()._static().type("String").name("method")
                                .build())
                        .build()
        ));
        Project result = this.repositoryParser.get(directory);
        assertThat(result.getName(), is(expected.getName()));
        assertThat(new HashSet<>(result.getMembers()), is(new HashSet<>(expected.getMembers())));
    }

}
