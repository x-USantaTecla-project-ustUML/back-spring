package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import org.junit.jupiter.api.BeforeEach;

class PlantUMLGeneratorTest extends UMLGeneratorTest {

    @BeforeEach
    void beforeEach() {
        this.UMLGenerator = new PlantUMLGenerator();
    }

    @Override
    protected String getExpectedEmptyClass(Class clazz) {
        return "class " + this.getName(clazz) + " {\n" +
                "}";
    }

    @Override
    protected String getExpectedCompleteClass(Class clazz) {
        return "abstract class " + this.getName(clazz) + " {\n" +
                "  - name: Type\n" +
                "  ~ {static} name: Type\n" +
                "  ~ name(): Type\n" +
                "  + name(name: Type): Type\n" +
                "  - {abstract} name(name: Type, name: Type): Type\n" +
                "}";
    }

    @Override
    protected String getExpectedRelationClass(Class clazz) {
        return "class " + this.getName(clazz) + " {\n" +
                "}";
    }

    @Override
    protected String getExpectedEmptyPackage(Package pakage) {
        return  "allow_mixing\n " +
                "package " + this.getName(pakage) + " {\n" +
                "}";
    }

    @Override
    protected String getExpectedCompletePackage(Package pakage) {
        return  "allow_mixing\n " +
                "package " + this.getName(pakage) + " {\n" +
                "  allow_mixing\n " +
                "    package " + this.getName(pakage.getMembers().get(0)) + " {\n" +
                "    }\n" +
                "  class " + this.getName(pakage.getMembers().get(1)) + " {\n" +
                "    }\n" +
                "}";
    }

    @Override
    protected String getExpectedRelationPackage(Package pakage) {
        Member originClass = pakage.getMembers().get(1);
        Member targetClass = pakage.getMembers().get(0);
        Member originActor = pakage.getMembers().get(3);
        Member targetUseCase = pakage.getMembers().get(2);
        return  "allow_mixing\n" +
                " package " + this.getName(pakage) + " {\n" +
                "  class " + this.getName(targetClass) + " {\n" +
                "    }\n" +
                "  class " + this.getName(originClass) + " {\n" +
                "    }\n" +
                "  allow_mixing\n" +
                "     actor \"originActor\" as null\n" +
                "  usecase \"targetUseCase\" as null\n" +
                "}\n" +
                    originClass.getId() + " .down.> " + targetClass.getId() + " : *..*\n" +
                    originActor.getId() + " .down.> " + targetUseCase.getId() + " : *..*";
    }

    @Override
    protected String getExpectedEmptyProject(Project project) {
        return  "allow_mixing\n " +
                "package " + this.getName(project) + " {\n" +
                "}";
    }

    @Override
    protected String getExpectedCompleteProject(Project project) {
        return  "allow_mixing\n " +
                "package " + this.getName(project) + " {\n" +
                "  allow_mixing\n " +
                "    package " + this.getName(project.getMembers().get(0)) + " {\n" +
                "    }\n" +
                "  class " + this.getName(project.getMembers().get(1)) + " {\n" +
                "    }\n" +
                "}";
    }

    @Override
    protected String getExpectedRelationProject(Project project) {
        Member origin = project.getMembers().get(1);
        Member target = project.getMembers().get(0);
        return  "allow_mixing\n " +
                "package " + this.getName(project) + " {\n" +
                "  allow_mixing\n " +
                "    package " + this.getName(target) + " {\n" +
                "    }\n" +
                "  allow_mixing\n " +
                "    package " + this.getName(origin) + " {\n" +
                "    }\n" +
                "}\n" +
                  origin.getId() + " .down.> " + target.getId() + " : *..*";
    }

    @Override
    protected String getExpectedRelationBetweenChildAndParent(Package target) {
        Member origin = target.getMembers().get(0);
        return  "allow_mixing\n " +
                "package " + this.getName(target) + " {\n" +
                "  allow_mixing\n " +
                "    package " + this.getName(origin) + " {\n" +
                "    }\n" +
                "}\n" +
                "package \"" + origin.getRelations().get(0).getTargetRoute() + "\" as " + target.getId() + "{}\n" +
                origin.getId() + " .down.> " + target.getId();
    }

    private String getName(Member member) {
        return "\"" + member.getName() + "\" as " + member.getId();
    }

}
