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

    @Test
    void testGivenGeneratorWhenGenerateEmptyClassThenReturn() {
        String name = "MyClass";
        Class clazz = new ClassBuilder()._package().name(name).build();
        String expected = this.getExpectedEmptyClass(clazz);
        assertThat(this.generator.generate(clazz), is(expected));
    }

    protected abstract String getExpectedEmptyClass(Class clazz);

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
        String expected = this.getExpectedCompleteClass(clazz);
        assertThat(this.generator.generate(clazz), is(expected));
    }

    protected abstract String getExpectedCompleteClass(Class clazz);

    @Test
    void testGivenGeneratorWhenGenerateRelationClassThenReturn() {
        String name = "MyClass";
        Class clazz = new ClassBuilder()._package().name(name)
                .use().target(new ClassBuilder().build()).build();
        String expected = this.getExpectedRelationClass(clazz);
        assertThat(this.generator.generate(clazz), is(expected));
    }

    protected abstract String getExpectedRelationClass(Class clazz);

    @Test
    void testGivenGeneratorWhenEmptyPackageThenReturn() {
        String name = "MyPackage";
        Package pakage = new PackageBuilder().name(name).build();
        String expected = this.getExpectedEmptyPackage(pakage);
        assertThat(this.generator.generate(pakage), is(expected));
    }

    protected abstract String getExpectedEmptyPackage(Package pakage);

    @Test
    void testGivenGeneratorWhenGenerateCompletePackageThenReturn() {
        String name = "MyPackage";
        String packageName = "package";
        String className = "class";
        Package pakage = new PackageBuilder().name(name)
                .pakage().name(packageName)
                .clazz().name(className).build();
        String expected = this.getExpectedCompletePackage(pakage);
        assertThat(this.generator.generate(pakage), is(expected));
    }

    protected abstract String getExpectedCompletePackage(Package pakage);

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
        String expected = this.getExpectedRelationPackage(pakage);
        assertThat(this.generator.generate(pakage), is(expected));
    }

    protected abstract String getExpectedRelationPackage(Package pakage);

    @Test
    void testGivenGeneratorWhenEmptyProjectThenReturn() {
        String name = "MyProject";
        Project project = new ProjectBuilder().name(name).build();
        String expected = this.getExpectedEmptyProject(project);
        assertThat(this.generator.generate(project), is(expected));
    }

    protected abstract String getExpectedEmptyProject(Project project);

    @Test
    void testGivenGeneratorWhenGenerateCompleteProjectThenReturn() {
        String name = "MyProject";
        String packageName = "package";
        String className = "class";
        Project project = new ProjectBuilder().name(name)
                .pakage().name(packageName)
                .clazz().name(className).build();
        String expected = this.getExpectedCompleteProject(project);
        assertThat(this.generator.generate(project), is(expected));
    }

    protected abstract String getExpectedCompleteProject(Project project);

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
        String expected = this.getExpectedRelationProject(project);
        assertThat(this.generator.generate(project), is(expected));
    }

    protected abstract String getExpectedRelationProject(Project project);

}
