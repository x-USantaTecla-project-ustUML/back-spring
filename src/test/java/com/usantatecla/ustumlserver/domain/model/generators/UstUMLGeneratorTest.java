package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import org.junit.jupiter.api.BeforeEach;

class UstUMLGeneratorTest extends UMLGeneratorTest {

    @BeforeEach
    void beforeEach() {
        this.UMLGenerator = new UstUMLGenerator();
    }

    @Override
    protected String getExpectedEmptyClass(Class clazz) {
        return "class: " + clazz.getName() + "\n" +
                "modifiers: package";
    }

    @Override
    protected String getExpectedCompleteClass(Class clazz) {
        return "class: " + clazz.getName() + "\n" +
                "modifiers: public abstract\n" +
                "members:\n" +
                "  - member: private Type name\n" +
                "  - member: package static Type name\n" +
                "  - member: package Type name()\n" +
                "  - member: public Type name(Type name)\n" +
                "  - member: private abstract Type name(Type name, Type name)";
    }

    @Override
    protected String getExpectedRelationClass(Class clazz) {
        return "class: " + clazz.getName() + "\n" +
                "modifiers: package\n" +
                "relations:\n" +
                "  - use: Name";
    }

    @Override
    protected String getExpectedEmptyPackage(Package pakage) {
        return "package: " + pakage.getName();
    }

    @Override
    protected String getExpectedCompletePackage(Package pakage) {
        return "package: " + pakage.getName() + "\n" +
                "members:\n" +
                "  - package: " + pakage.getMembers().get(0).getName() + "\n" +
                "  - class: " + pakage.getMembers().get(1).getName() + "\n" +
                "    modifiers: package";
    }

    @Override
    protected String getExpectedRelationPackage(Package pakage) {
        String targetName = pakage.getMembers().get(0).getName();
        String originName = pakage.getMembers().get(1).getName();
        return "package: " + pakage.getName() + "\n" +
                "members:\n" +
                "  - class: " + targetName + "\n" +
                "    modifiers: package\n" +
                "  - class: " + originName + "\n" +
                "    modifiers: package\n" +
                "    relations:\n" +
                "      - use: " + targetName + "\n" +
                "        role: *..*\n" +
                "  - actor: originActor\n" +
                "    relations:\n" +
                "      - use: targetUseCase\n" +
                "        role: *..*\n" +
                "  - usecase: targetUseCase";
    }

    @Override
    protected String getExpectedEmptyProject(Project project) {
        return "project: " + project.getName();
    }

    @Override
    protected String getExpectedCompleteProject(Project project) {
        return "project: " + project.getName() + "\n" +
                "members:\n" +
                "  - package: " + project.getMembers().get(0).getName() + "\n" +
                "  - class: " + project.getMembers().get(1).getName() + "\n" +
                "    modifiers: package";
    }

    @Override
    protected String getExpectedRelationProject(Project project) {
        String targetName = project.getMembers().get(0).getName();
        String originName = project.getMembers().get(1).getName();
        return "project: " + project.getName() + "\n" +
                "members:\n" +
                "  - package: " + targetName + "\n" +
                "  - package: " + originName + "\n" +
                "    relations:\n" +
                "      - use: " + targetName + "\n" +
                "        role: *..*";
    }

    @Override
    protected String getExpectedRelationBetweenChildAndParent(Package target) {
        String targetName = target.getName();
        String originName = target.getMembers().get(0).getName();
        return "package: " + targetName + "\n" +
                "members:\n" +
                "  - package: " + originName + "\n" +
                "    relations:\n" +
                "      - use: " + targetName;
    }

}
