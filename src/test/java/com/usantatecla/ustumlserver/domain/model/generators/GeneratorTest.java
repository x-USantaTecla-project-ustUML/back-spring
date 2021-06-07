package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ProjectBuilder;
import com.usantatecla.ustumlserver.domain.model.generators.Generator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

abstract class GeneratorTest {

    protected Generator generator;
    protected Class clazz;

    @Test
    void testGivenGeneratorWhenGenerateEmptyClassThenReturn() {
        String name = "MyClass";
        Class clazz = new ClassBuilder()._package().name(name).build();
        String expected = "class: "+ name +"\n" +
                "modifiers: package";
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
        String expected = "class: " + name + "\n" +
                "modifiers: public abstract\n" +
                "members:\n" +
                "  - member: private Type name\n" +
                "  - member: package static Type name\n" +
                "  - member: package Type name()\n" +
                "  - member: public Type name(Type name)\n" +
                "  - member: private abstract Type name(Type name, Type name)";
        assertThat(this.generator.generate(clazz), is(expected));
    }

    @Test
    void testGivenGeneratorWhenGenerateRelationClassThenReturn() {
        String name = "MyClass";
        Class clazz = new ClassBuilder()._package().name(name)
                .use().target(new ClassBuilder().build()).build();
        String expected = "class: "+ name +"\n" +
                "modifiers: package\n" +
                "relations:\n" +
                "  - use: Name";
        assertThat(this.generator.generate(clazz), is(expected));
    }

    @Test
    void testGivenGeneratorWhenEmptyPackageThenReturn() {
        String name = "MyPackage";
        Package pakage = new PackageBuilder().name(name).build();
        String expected = "package: "+ name;
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
        String expected = "package: " + name + "\n" +
                "members:\n" +
                "  - package: " + packageName + "\n" +
                "  - class: " + className + "\n" +
                "    modifiers: package";
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
        String expected = "package: " + name + "\n" +
                "members:\n" +
                "  - class: " + targetName + "\n" +
                "    modifiers: package\n" +
                "  - class: " + originName + "\n" +
                "    modifiers: package\n" +
                "    relations:\n" +
                "      - use: " + targetName + "\n" +
                "        role: " + role;
        assertThat(this.generator.generate(pakage), is(expected));
    }

    @Test
    void testGivenGeneratorWhenEmptyProjectThenReturn() {
        String name = "MyProject";
        Project project = new ProjectBuilder().name(name).build();
        String expected = "project: "+ name;
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
        String expected = "project: " + name + "\n" +
                "members:\n" +
                "  - package: " + packageName + "\n" +
                "  - class: " + className + "\n" +
                "    modifiers: package";
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
        String expected = "project: " + name + "\n" +
                "members:\n" +
                "  - package: " + targetName + "\n" +
                "  - package: " + originName + "\n" +
                "    relations:\n" +
                "      - use: " + targetName + "\n" +
                "        role: " + role;
        assertThat(this.generator.generate(project), is(expected));
    }

}
