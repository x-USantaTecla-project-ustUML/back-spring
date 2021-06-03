package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DirectoryTreeGeneratorTest {

    private DirectoryTreeGenerator directoryTreeGenerator = new DirectoryTreeGenerator();
    private Account account;

    @BeforeEach
    void beforeEach() {
        this.account = Account.builder()
                .email("test@test.com")
                .name("username")
                .projects(new ArrayList<>())
                .build();
    }

    @Test
    void testGivenEmptyAccountWhenGenerateThenReturnCorrectJSON() {
        String expected = "{\"name\": \"test@test.com\"}";

        assertThat(this.directoryTreeGenerator.generate(this.account), is(expected));
    }

    @Test
    void testGivenAccountWithEmptyProjectsWhenGenerateThenReturnCorrectJSON() {
        String project1 = "project1";
        String project2 = "project2";
        this.account.add(Project.builder().name(project1).members(new ArrayList<>()).build());
        this.account.add(Project.builder().name(project2).members(new ArrayList<>()).build());
        String expected =
                "{\"name\": \"test@test.com\", " +
                    "\"children\": [" +
                        "{\"name\": \"" + project1 + "\"}, " +
                        "{\"name\": \"" + project2 + "\"}" +
                    "]" +
                "}";

        assertThat(this.directoryTreeGenerator.generate(this.account), is(expected));
    }

    @Test
    void testGivenAccountWithProjectsWhenGenerateThenReturnCorrectJSON() {
        String project1 = "project1";
        String project2 = "project2";
        String childProject1 = "p1";
        List<Member> project1_members = List.of(new PackageBuilder().name("p1").build());
        List<Member> project2_members = List.of(new ClassBuilder().name("c1").build());
        this.account.add(Project.builder().name(project1).members(project1_members).build());
        this.account.add(Project.builder().name(project2).members(project2_members).build());
        String expected =
                "{\"name\": \"test@test.com\", " +
                    "\"children\": [" +
                        "{\"name\": \"" + project1 + "\""+
                        ", \"children\": [{\"name\": \"" + childProject1 + "\"}]"+"}, " +
                        "{\"name\": \"" + project2 + "\"}" +
                    "]" +
                "}";

        assertThat(this.directoryTreeGenerator.generate(this.account), is(expected));
    }

    @Test
    void testGivenEmptyPackageWhenGenerateThenReturnCorrectJSON() {
        String packageName = "packageName";
        Package _package = new PackageBuilder().name(packageName).build();
        String expected = "{\"name\": \"" + packageName + "\"}";

        assertThat(this.directoryTreeGenerator.generate(_package), is(expected));
    }

    @Test
    void testGivenPackageWithClassesWhenGenerateThenReturnCorrectJSON() {
        String packageName = "packageName";
        Package _package = new PackageBuilder().name(packageName).clazz().name("MyClass").build();
        _package.add(new ClassBuilder().name("AnotherClass").build());
        String expected = "{\"name\": \"" + packageName + "\"}";

        assertThat(this.directoryTreeGenerator.generate(_package), is(expected));
    }

    @Test
    void testGivenPackageWithPackagesWhenGenerateThenReturnCorrectJSON() {
        String packageName = "packageName";
        String packageName2 = "packageName2";
        String packageName3 = "packageName3";
        String packageName4 = "packageName4";
        Package _package = new PackageBuilder().name(packageName).clazz().name("MyClass").build();
        _package.add(new PackageBuilder().name(packageName2).clazz().name("AnotherClass").build());
        _package.add(new PackageBuilder().name(packageName3)
                .pakage(new PackageBuilder().name(packageName4)
                        .build())
                .build());
        String expected =
                "{\"name\": \"" + packageName + "\", " +
                    "\"children\": [" +
                        "{\"name\": \"" + packageName2 + "\"}, " +
                        "{\"name\": \"" + packageName3 + "\", " +
                            "\"children\": [" +
                                "{\"name\": \"" + packageName4 + "\"}" +
                            "]" +
                        "}" +
                    "]" +
                "}";

        assertThat(this.directoryTreeGenerator.generate(_package), is(expected));
    }

}
