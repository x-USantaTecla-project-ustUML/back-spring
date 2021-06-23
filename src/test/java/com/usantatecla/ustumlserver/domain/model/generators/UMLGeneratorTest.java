package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ProjectBuilder;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

abstract class UMLGeneratorTest {

    static final String ID = "id";

    protected UMLGenerator UMLGenerator;

    @Test
    void testGivenGeneratorWhenGenerateEmptyClassThenReturn() {
        String name = "MyClass";
        Class clazz = new ClassBuilder()._package().id(UMLGeneratorTest.ID).name(name).build();
        String expected = this.getExpectedEmptyClass(clazz);
        assertThat(this.UMLGenerator.generate(clazz), is(expected));
    }

    protected abstract String getExpectedEmptyClass(Class clazz);

    @Test
    void testGivenGeneratorWhenGenerateCompleteClassThenReturn() {
        String name = "MyClass";
        Class clazz = new ClassBuilder()._public()._abstract().id(UMLGeneratorTest.ID).name(name)
                .attribute()._private()
                .attribute()._static()
                .method()
                .method()._public().parameter()
                .method()._private()._abstract().parameter().parameter()
                .build();
        String expected = this.getExpectedCompleteClass(clazz);
        assertThat(this.UMLGenerator.generate(clazz), is(expected));
    }

    protected abstract String getExpectedCompleteClass(Class clazz);

    @Test
    void testGivenGeneratorWhenGenerateRelationClassThenReturn() {
        String name = "MyClass";
        Class clazz = new ClassBuilder()._package().id(UMLGeneratorTest.ID).name(name)
                .use().target(new ClassBuilder().build()).build();
        String expected = this.getExpectedRelationClass(clazz);
        assertThat(this.UMLGenerator.generate(clazz), is(expected));
    }

    protected abstract String getExpectedRelationClass(Class clazz);

    @Test
    void testGivenGeneratorWhenEmptyPackageThenReturn() {
        String name = "MyPackage";
        Package pakage = new PackageBuilder().id(UMLGeneratorTest.ID).name(name).build();
        String expected = this.getExpectedEmptyPackage(pakage);
        assertThat(this.UMLGenerator.generate(pakage), is(expected));
    }

    protected abstract String getExpectedEmptyPackage(Package pakage);

    @Test
    void testGivenGeneratorWhenGenerateCompletePackageThenReturn() {
        String name = "MyPackage";
        String packageName = "package";
        String className = "class";
        Package pakage = new PackageBuilder().id(UMLGeneratorTest.ID).name(name)
                .pakage().id(UMLGeneratorTest.ID).name(packageName)
                .clazz().id(UMLGeneratorTest.ID).name(className).build();
        String expected = this.getExpectedCompletePackage(pakage);
        assertThat(this.UMLGenerator.generate(pakage), is(expected));
    }

    protected abstract String getExpectedCompletePackage(Package pakage);

    @Test
    void testGivenGeneratorWhenGenerateRelationPackageThenReturn() {
        String name = "MyPackage";
        String originName = "originClass";
        String targetName = "targetClass";
        String role = "*..*";
        Class target = new ClassBuilder().id(UMLGeneratorTest.ID).name(targetName).build();
        Class origin = new ClassBuilder().id(UMLGeneratorTest.ID).name(originName)
                .use().target(target).role(role).build();
        Package pakage = new PackageBuilder().id(UMLGeneratorTest.ID).name(name)
                .classes(target, origin).build();
        String expected = this.getExpectedRelationPackage(pakage);
        assertThat(this.UMLGenerator.generate(pakage), is(expected));
    }

    protected abstract String getExpectedRelationPackage(Package pakage);

    @Test
    void testGivenGeneratorWhenEmptyProjectThenReturn() {
        String name = "MyProject";
        Project project = new ProjectBuilder().id(UMLGeneratorTest.ID).name(name).build();
        String expected = this.getExpectedEmptyProject(project);
        assertThat(this.UMLGenerator.generate(project), is(expected));
    }

    protected abstract String getExpectedEmptyProject(Project project);

    @Test
    void testGivenGeneratorWhenGenerateCompleteProjectThenReturn() {
        String name = "MyProject";
        String packageName = "package";
        String className = "class";
        Project project = new ProjectBuilder().id(UMLGeneratorTest.ID).name(name)
                .pakage().id(UMLGeneratorTest.ID).name(packageName)
                .clazz().id(UMLGeneratorTest.ID).name(className).build();
        String expected = this.getExpectedCompleteProject(project);
        assertThat(this.UMLGenerator.generate(project), is(expected));
    }

    protected abstract String getExpectedCompleteProject(Project project);

    @Test
    void testGivenGeneratorWhenGenerateRelationProjectThenReturn() {
        String name = "MyProject";
        String originName = "originPackage";
        String targetName = "targetPackage";
        String role = "*..*";
        Package target = new PackageBuilder().id(UMLGeneratorTest.ID).name(targetName).build();
        Package origin = new PackageBuilder().id(UMLGeneratorTest.ID).name(originName)
                .use().target(target).role(role).build();
        Project project = new ProjectBuilder().id(UMLGeneratorTest.ID).name(name)
                .packages(target, origin).build();
        String expected = this.getExpectedRelationProject(project);
        assertThat(this.UMLGenerator.generate(project), is(expected));
    }

    protected abstract String getExpectedRelationProject(Project project);

    @Test
    void testGivenGeneratorWhenGenerateRelationBetweenChildAndParentThenReturn() {
        String originName = "OriginPackage";
        String targetName = "TargetPackage";
        String targetRoute = "com.usantatecla.model";
        Package target = new PackageBuilder().id(UMLGeneratorTest.ID).name(targetName).build();
        Package origin = new PackageBuilder().id(UMLGeneratorTest.ID).name(originName)
                .use().target(target).route(targetRoute).build();
        target.add(origin);
        String expected = this.getExpectedRelationBetweenChildAndParent(target);
        assertThat(this.UMLGenerator.generate(target), is(expected));
    }

    protected abstract String getExpectedRelationBetweenChildAndParent(Package pakage);

}
