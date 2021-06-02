package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.ModelException;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.PackageBuilder;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandBuilder;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PackageParserTest {

    @Test
    void testGivenPackageParserWhenGetThenReturn() {
        String name = "a";
        Command command = new CommandBuilder().command(
                "{ package: " + name + "}").build();
        Package expected = new PackageBuilder().name(name).build();
        assertThat(new PackageParser().get(command), is(expected));
    }

    @Test
    void testGivenPackageParserWhenGetWithPackageReturn() {
        String packageName = "MyPackage";
        String firstName = "First";
        String secondName = "Second";
        Command command = new CommandBuilder().command("{" +
                "   package: " + packageName + "," +
                "   members: [" +
                "       {class: " + firstName + "}," +
                "       {class: " + secondName + "}" +
                "   ]" +
                "   }" +
                "}").build();
        Package expected = new PackageBuilder().name(packageName).clazz().name(firstName).clazz().name(secondName).build();
        assertThat(new PackageParser().get(command), is(expected));
    }

    @Test
    void testGivenPackageParserWhenGetWithTwoPackageThenReturn() {
        String packageName = "MyPackage";
        String firstName = "First";
        String secondName = "Second";
        String thirdName = "Third";
        Command command = new CommandBuilder().command("{" +
                "   package: " + packageName + "," +
                "   members: [" +
                "       {package: " + firstName + "," +
                "        members: [" +
                "           {package: " + thirdName + "}" +
                "        ]}," +
                "       {class: " + secondName + "}" +
                "   ]" +
                "   }" +
                "}").build();
        Package expected = new PackageBuilder().name(packageName)
                .pakage(new PackageBuilder().name(firstName)
                        .pakage(new PackageBuilder().name(thirdName)
                                .build())
                        .build())
                .clazz().name(secondName)
                .build();
        assertThat(new PackageParser().get(command), is(expected));
    }

    @Test
    void testGivenPackageParserWhenGetExistClassThenThrowsMemberAlreadyExists() {
        String packageName = "MyPackage";
        Command command = new CommandBuilder().command("{" +
                "   package: " + packageName +  "," +
                "   members: [" +
                "       {class: name }," +
                "       {class: name }" +
                "   ]" +
                "   }" +
                "}").build();
        PackageParser packageParser = new PackageParser();
        assertThrows(ModelException.class, () -> packageParser.get(command));
    }

    @Test
    void testGivenPackageParserWhenGetPackageThenThrowBadPackageNameValue() {
        for (String name : new String[]{
                "null",
                "9",
                "#name",
                " ",
                ""}) {
            String input = "{" +
                    "   class: \"" + name + "\"" +
                    "}";
            Command command = new CommandBuilder().command(input).build();
            PackageParser packageParser = new PackageParser();
            assertThrows(ParserException.class, () -> packageParser.get(command), "error: " + name);
        }
    }
}
