package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import com.usantatecla.ustumlserver.domain.model.generators.DirectoryTreeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DirectoryTreeGeneratorTest {

    private static final String EMAIL = "test@test.com";
    private static final String ID = "test";

    private DirectoryTreeGenerator directoryTreeGenerator = new DirectoryTreeGenerator();
    private Account account;

    @BeforeEach
    void beforeEach() {
        this.account = Account.builder()
                .id(DirectoryTreeGeneratorTest.ID)
                .email(DirectoryTreeGeneratorTest.EMAIL)
                .name("username")
                .projects(new ArrayList<>())
                .build();
    }

    @Test
    void testGivenEmptyAccountWhenGenerateThenReturnCorrectJSON() {
        String expected = "{" +
                    "\"id\": \"" + DirectoryTreeGeneratorTest.ID + "\", " +
                    "\"name\": \"" + DirectoryTreeGeneratorTest.EMAIL + "\"" +
                "}";

        assertThat(this.directoryTreeGenerator.generate(this.account), is(expected));
    }

    @Test
    void testGivenAccountWithEmptyProjectsWhenGenerateThenReturnCorrectJSON() {
        String project1 = "project1";
        String project2 = "project2";
        this.account.add(Project.builder().id(project1).name(project1)
                .members(new ArrayList<>()).build());
        this.account.add(Project.builder().id(project2).name(project2)
                .members(new ArrayList<>()).build());
        String expected =
                "{" +
                    "\"id\": \"" + DirectoryTreeGeneratorTest.ID + "\", " +
                    "\"name\": \"" + DirectoryTreeGeneratorTest.EMAIL + "\", " +
                    "\"children\": [" +
                        "{" +
                            "\"id\": \"" + project1 + "\", " +
                            "\"name\": \"" + project1 + "\"" +
                        "}, " +
                        "{" +
                            "\"id\": \"" + project2 + "\", " +
                            "\"name\": \"" + project2 + "\"" +
                        "}" +
                    "]" +
                "}";

        assertThat(this.directoryTreeGenerator.generate(this.account), is(expected));
    }

    @Test
    void testGivenAccountWithProjectsWhenGenerateThenReturnCorrectJSON() {
        String project1 = "project1";
        String project2 = "project2";
        String childProject1 = "p1";
        List<Member> project1_members = List.of(new PackageBuilder().id(childProject1).name(childProject1).build());
        List<Member> project2_members = List.of(new ClassBuilder().name("c1").build());
        this.account.add(Project.builder().id(project1).name(project1)
                .members(project1_members).build());
        this.account.add(Project.builder().id(project2).name(project2)
                .members(project2_members).build());
        String expected =
                "{" +
                    "\"id\": \"" + DirectoryTreeGeneratorTest.ID + "\", " +
                    "\"name\": \"" + DirectoryTreeGeneratorTest.EMAIL + "\", " +
                    "\"children\": [" +
                        "{" +
                            "\"id\": \"" + project1 + "\", " +
                            "\"name\": \"" + project1 + "\", " +
                            "\"children\": [" +
                                "{" +
                                    "\"id\": \"" + childProject1 + "\", " +
                                    "\"name\": \"" + childProject1 + "\"" +
                                "}" +
                            "]"+
                        "}, " +
                        "{" +
                            "\"id\": \"" + project2 + "\", " +
                            "\"name\": \"" + project2 + "\"" +
                        "}" +
                    "]" +
                "}";

        assertThat(this.directoryTreeGenerator.generate(this.account), is(expected));
    }

    @Test
    void testGivenEmptyPackageWhenGenerateThenReturnCorrectJSON() {
        String packageName = "packageName";
        Package _package = new PackageBuilder().id(packageName).name(packageName).build();
        String expected = "{" +
                    "\"id\": \"" + packageName + "\", " +
                    "\"name\": \"" + packageName + "\"" +
                "}";

        assertThat(this.directoryTreeGenerator.generate(_package), is(expected));
    }

    @Test
    void testGivenPackageWithClassesWhenGenerateThenReturnCorrectJSON() {
        String packageName = "packageName";
        Package _package = new PackageBuilder().id(packageName).name(packageName)
                .clazz().name("MyClass").build();
        _package.add(new ClassBuilder().name("AnotherClass").build());
        String expected = "{" +
                    "\"id\": \"" + packageName + "\", " +
                    "\"name\": \"" + packageName + "\"" +
                "}";

        assertThat(this.directoryTreeGenerator.generate(_package), is(expected));
    }

    @Test
    void testGivenPackageWithPackagesWhenGenerateThenReturnCorrectJSON() {
        String packageName = "packageName";
        String packageName2 = "packageName2";
        String packageName3 = "packageName3";
        String packageName4 = "packageName4";
        Package _package = new PackageBuilder().id(packageName).name(packageName)
                .clazz().name("MyClass").build();
        _package.add(new PackageBuilder().id(packageName2).name(packageName2)
                .clazz().name("AnotherClass").build());
        _package.add(new PackageBuilder().id(packageName3).name(packageName3)
                .pakage(new PackageBuilder().id(packageName4).name(packageName4)
                        .build())
                .build());
        String expected =
                "{" +
                    "\"id\": \"" + packageName + "\", " +
                    "\"name\": \"" + packageName + "\", " +
                    "\"children\": [" +
                        "{" +
                            "\"id\": \"" + packageName2 + "\", " +
                            "\"name\": \"" + packageName2 + "\"" +
                        "}, " +
                        "{" +
                            "\"id\": \"" + packageName3 + "\", " +
                            "\"name\": \"" + packageName3 + "\", " +
                            "\"children\": [" +
                                "{" +
                                    "\"id\": \"" + packageName4 + "\", " +
                                    "\"name\": \"" + packageName4 + "\"" +
                                "}" +
                            "]" +
                        "}" +
                    "]" +
                "}";

        assertThat(this.directoryTreeGenerator.generate(_package), is(expected));
    }

}
