package com.usantatecla.ustumlserver.domain.services.reverseEngineering;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.ClassBuilder;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileParserTest {

    private static final String TEST_FILES_PATH = "src/test/resources/reverseEngineering/";

    private FileParser fileParser;

    @BeforeEach
    void beforeEach() {
        this.fileParser = new FileParser();
    }

    @Test
    void testGivenFileParserWhenGetWithWrongPathThenThrowException() {
        assertThrows(ServiceException.class, () -> this.fileParser.get(new File("notFoundRoute")));
    }

    @SneakyThrows
    @Test
    void testGivenFileParserWhenGetFileWithEmptyJavaClassThenReturn() {
        String className = "EmptyClass";
        File file = new File(FileParserTest.TEST_FILES_PATH + className + ".java");
        Class expected = new Class(className, new ArrayList<>(), new ArrayList<>());
        assertThat(this.fileParser.get(file), is(expected));
    }

    @SneakyThrows
    @Test
    void testGivenFileParserWhenGetFileWithEmptyJavaInterfaceThenReturn() {
        // TODO Cambiar a interfaz cuando esté implementado
        String interfaceName = "EmptyInterface";
        File file = new File(FileParserTest.TEST_FILES_PATH + interfaceName + ".java");
        Class expected = new Class(interfaceName, new ArrayList<>(), new ArrayList<>());
        assertThat(this.fileParser.get(file), is(expected));
    }

    @SneakyThrows
    @Test
    void testGivenFileParserWhenGetFileWithEmptyJavaEnumThenReturn() {
        // TODO Cambiar a enum cuando esté implementado
        String enumName = "EmptyEnum";
        File file = new File(FileParserTest.TEST_FILES_PATH + enumName + ".java");
        Class expected = new Class(enumName, new ArrayList<>(), new ArrayList<>());
        assertThat(this.fileParser.get(file), is(expected));
    }

    @SneakyThrows
    @Test
    void testGivenFileParserWhenGetFileWithEmptyJavaAnnotationThenReturn() {
        // TODO Cambiar a interfaz cuando esté implementado
        String annotationName = "EmptyAnnotation";
        File file = new File(FileParserTest.TEST_FILES_PATH + annotationName + ".java");
        Class expected = new Class(annotationName, new ArrayList<>(), new ArrayList<>());
        assertThat(this.fileParser.get(file), is(expected));
    }

    @SneakyThrows
    @Test
    void testGivenFileParserWhenGetFileWithJavaClassWithAttributesThenReturn() {
        String className = "AttributesClass";
        File file = new File(FileParserTest.TEST_FILES_PATH + className + ".java");
        Class expected = new ClassBuilder()._public()._abstract().name(className)
                .attribute()._private()._static()._final().type("int").name("CONSTANT")
                .attribute()._private().type("int").name("privateInt")
                .attribute()._public().type("String").name("publicString")
                .attribute()._protected().type("Integer").name("protectedInteger")
                .attribute()._package().type("EmptyClass").name("packageEmptyClass")
                .build();
        assertThat(this.fileParser.get(file), is(expected));
    }

    @SneakyThrows
    @Test
    void testGivenFileParserWhenGetFileWithJavaClassWithMethodsThenReturn() {
        String className = "MethodsClass";
        File file = new File(FileParserTest.TEST_FILES_PATH + className + ".java");
        Class expected = new ClassBuilder()._public()._abstract().name(className)
                .method()._public()._static().type("void").name("publicStaticVoid")
                .method()._protected().type("String").name("protectedString").parameter().parameter()
                .method()._package()._abstract().type("EmptyClass").name("privateAbstractEmptyClass").parameter()
                .build();
        assertThat(this.fileParser.get(file), is(expected));
    }

    @SneakyThrows
    @Test
    void testGivenFileParserWhenGetFileWithCompleteJavaClassThenReturn() {
        // TODO Añadir relation cuando se implementen (dependencia con EmptyClass)
        String className = "CompleteClass";
        File file = new File(FileParserTest.TEST_FILES_PATH + className + ".java");
        Class expected = new ClassBuilder()._public()._abstract().name(className)
                .attribute()._package().type("EmptyClass").name("packageEmptyClass")
                .method()._package()._abstract().type("EmptyClass").name("privateAbstractEmptyClass").parameter()
                .build();
        assertThat(this.fileParser.get(file), is(expected));
    }


}
