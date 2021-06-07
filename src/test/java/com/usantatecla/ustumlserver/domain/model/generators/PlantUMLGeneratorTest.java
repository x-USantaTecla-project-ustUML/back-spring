package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PlantUMLGeneratorTest extends GeneratorTest{

    @BeforeEach
    void beforeEach() {
        this.generator = new PlantUMLGenerator();
    }

    @Test
    void testGivenGeneratorWhenGenerateEmptyClassThenReturn() {
        String name = "MyClass";
        Class clazz = new ClassBuilder()._package().name(name).build();
        String expected = "class "+ name +" {\n" +
                "}";
        assertThat(this.generator.generate(clazz), is(expected));
    }

    @Test
    void testGivenGeneratorWhenGenerateCompleteClassThenReturn() {
        String name = "MyClass";
        Class clazz = new ClassBuilder()._public()._abstract().name(name)
                .attribute()._private()
                .attribute()._static()
                .method()
                .method()._public().parameter()
                .method()._private()._abstract().parameter().parameter()
                .build();
        String expected = "abstract class " + name + " {\n" +
                "  - name: Type\n" +
                "  ~ {static} name: Type\n" +
                "  ~ name(): Type\n" +
                "  + name(name: Type): Type\n" +
                "  - {abstract} name(name: Type, name: Type): Type\n" +
                "}";
        assertThat(this.generator.generate(clazz), is(expected));
    }

    @Test
    void testGivenGeneratorWhenGenerateRelationClassThenReturn() {
        String name = "MyClass";
        Class clazz = new ClassBuilder()._package().name(name)
                .use().target(new ClassBuilder().build()).build();
        String expected = "class "+ name +" {\n" +
                "}";
        assertThat(this.generator.generate(clazz), is(expected));
    }

    @Test
    void testGivenGeneratorWhenEmptyPackageThenReturn() {
        String name = "MyPackage";
        Package pakage = new PackageBuilder().name(name).build();
        String expected = "package "+ name +" {\n" +
                "}";
        assertThat(this.generator.generate(pakage), is(expected));
    }

    @Test
    void testGivenGeneratorWhenGenerateCompletePackageThenReturn() {
        String name = "MyPackage";
        String packageName = "package";
        String className = "class";
        Package pakage = new PackageBuilder().name(name)
                .pakage().name(packageName)
                .clazz().name(className).build();
        String expected = "package " + name + " {\n" +
                "  package " + packageName + " {\n" +
                "    }\n" +
                "  class " + className + " {\n" +
                "    }\n" +
                "}";
        assertThat(this.generator.generate(pakage), is(expected));
    }

    @Test
    void testGivenGeneratorWhenGenerateRelationPackageThenReturn() {
        String name = "MyPackage";
        String originName = "originClass";
        String targetName = "targetClass";
        String role = "*..*";
        Class target = new ClassBuilder().name(targetName).build();
        Class origin = new ClassBuilder().name(originName)
                .use().target(target).role(role).build();
        Package pakage = new PackageBuilder().name(name)
                .classes(target, origin).build();
        String expected = "package " + name + " {\n" +
                "  class " + targetName + " {\n" +
                "    }\n" +
                "  class " + originName + " {\n" +
                "    }\n" +
                "\"" + originName + "\" .down.> \"" + targetName + "\" : *..*\n" +
                "}";
        assertThat(this.generator.generate(pakage), is(expected));
    }

    @Test
    void testGivenGeneratorWhenEmptyProjectThenReturn() {
        String name = "MyProject";
        Project project = new ProjectBuilder().name(name).build();
        String expected = "package "+ name +" {\n" +
                "}";
        assertThat(this.generator.generate(project), is(expected));
    }

    @Test
    void testGivenGeneratorWhenGenerateCompleteProjectThenReturn() {
        String name = "MyProject";
        String packageName = "package";
        String className = "class";
        Project project = new ProjectBuilder().name(name)
                .pakage().name(packageName)
                .clazz().name(className).build();
        String expected = "package " + name + " {\n" +
                "  package " + packageName + " {\n" +
                "    }\n" +
                "  class " + className + " {\n" +
                "    }\n" +
                "}";
        assertThat(this.generator.generate(project), is(expected));
    }

    @Test
    void testGivenGeneratorWhenGenerateRelationProjectThenReturn() {
        String name = "MyProject";
        String originName = "originPackage";
        String targetName = "targetPackage";
        String role = "*..*";
        Package target = new PackageBuilder().name(targetName).build();
        Package origin = new PackageBuilder().name(originName)
                .use().target(target).role(role).build();
        Project project = new ProjectBuilder().name(name)
                .packages(target, origin).build();
        String expected = "package " + name + " {\n" +
                "  package " + targetName + " {\n" +
                "    }\n" +
                "  package " + originName + " {\n" +
                "    }\n" +
                "\"" + originName + "\" .down.> \"" + targetName + "\" : *..*\n" +
                "}";
        assertThat(this.generator.generate(project), is(expected));
    }

}
