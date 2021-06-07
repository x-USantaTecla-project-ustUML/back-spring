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

    @Override
    protected String getExpectedEmptyClass(Class clazz) {
        return "class "+ clazz.getName() +" {\n" +
                "}";
    }

    @Override
    protected String getExpectedCompleteClass(Class clazz) {
        return "abstract class " + clazz.getName() + " {\n" +
                "  - name: Type\n" +
                "  ~ {static} name: Type\n" +
                "  ~ name(): Type\n" +
                "  + name(name: Type): Type\n" +
                "  - {abstract} name(name: Type, name: Type): Type\n" +
                "}";
    }

    @Override
    protected String getExpectedRelationClass(Class clazz) {
        return "class "+ clazz.getName() +" {\n" +
                "}";
    }

    @Override
    protected String getExpectedEmptyPackage(Package pakage) {
        return "package "+ pakage.getName() +" {\n" +
                "}";
    }

    @Override
    protected String getExpectedCompletePackage(Package pakage) {
        return "package " + pakage.getName() + " {\n" +
                "  package " + pakage.getMembers().get(0).getName() + " {\n" +
                "    }\n" +
                "  class " + pakage.getMembers().get(1).getName() + " {\n" +
                "    }\n" +
                "}";
    }

    @Override
    protected String getExpectedRelationPackage(Package pakage) {
        String originName = pakage.getMembers().get(1).getName();
        String targetName = pakage.getMembers().get(0).getName();
        return "package " + pakage.getName() + " {\n" +
                "  class " + targetName + " {\n" +
                "    }\n" +
                "  class " + originName + " {\n" +
                "    }\n" +
                "\"" + originName + "\" .down.> \"" + targetName + "\" : *..*\n" +
                "}";
    }

    @Override
    protected String getExpectedEmptyProject(Project project) {
        return "package "+ project.getName() +" {\n" +
                "}";
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

    @Override
    protected String getExpectedCompleteProject(Project project) {
        return "package " + project.getName() + " {\n" +
                "  package " + project.getMembers().get(0).getName() + " {\n" +
                "    }\n" +
                "  class " + project.getMembers().get(1).getName() + " {\n" +
                "    }\n" +
                "}";
    }

    @Override
    protected String getExpectedRelationProject(Project project) {
        String originName = project.getMembers().get(1).getName();
        String targetName = project.getMembers().get(0).getName();
        return "package " + project.getName() + " {\n" +
                "  package " + targetName + " {\n" +
                "    }\n" +
                "  package " + originName + " {\n" +
                "    }\n" +
                "\"" + originName + "\" .down.> \"" + targetName + "\" : *..*\n" +
                "}";
    }

}
