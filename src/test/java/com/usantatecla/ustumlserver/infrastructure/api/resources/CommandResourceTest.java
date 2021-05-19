package com.usantatecla.ustumlserver.infrastructure.api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandResponseDto;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RestTestConfig
public class CommandResourceTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private Seeder seeder;

    @BeforeEach
    void beforeEach() {
        this.seeder.initialize();
    }

    /*@SneakyThrows
    @Test
    void testGivenCommandResourceWhenExecuteCommandThenReturn() {
        JSONObject input = new JSONObject(
                "{" +
                        "add: {" +
                        "       members: [" +
                        "           {class: Name}" +
                        "       ]" +
                        "   }" +
                        "}"
        );
        CommandResponseDto expected = new CommandResponseDto(
                "package name {\n" +
                        "  class Name {\n" +
                        "    }\n" +
                        "}",
                "package: name\n" +
                        "members:\n" +
                        "  - class: Name\n" +
                        "    modifiers: package");
        this.webTestClient.post()
                .uri(CommandResource.COMMAND)
                .bodyValue(new ObjectMapper().readValue(input.toString(), Map.class))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommandResponseDto.class)
                .value(Assertions::assertNotNull)
                .value(commandResponseDto ->
                        assertThat(commandResponseDto, is(expected)));
    }

    @SneakyThrows
    @Test
    void testGivenCommandResourceWhenExecuteCommandWithPackagesThenReturn() {
        JSONObject input = new JSONObject(
                "{" +
                        "add: {" +
                        "       members: [" +
                        "           {" +
                        "               package: otherPackage," +
                        "               members: [" +
                        "                   {class: InsidePackage}" +
                        "               ]" +
                        "           }," +
                        "           {class: Name}" +
                        "       ]" +
                        "   }" +
                        "}"
        );
        CommandResponseDto expected = new CommandResponseDto(
                "package name {\n" +
                        "  package otherPackage {\n" +
                        "      class InsidePackage {\n" +
                        "        }\n" +
                        "    }\n" +
                        "  class Name {\n" +
                        "    }\n" +
                        "}",
                "package: name\n" +
                        "members:\n" +
                        "  - package: otherPackage\n" +
                        "    members:\n" +
                        "      - class: InsidePackage\n" +
                        "        modifiers: package\n" +
                        "  - class: Name\n" +
                        "    modifiers: package");
        this.webTestClient.post()
                .uri(CommandResource.COMMAND)
                .bodyValue(new ObjectMapper().readValue(input.toString(), Map.class))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommandResponseDto.class)
                .value(Assertions::assertNotNull)
                .value(commandResponseDto ->
                        assertThat(commandResponseDto, is(expected)));
    }*/

    @SneakyThrows
    @Test
    void testGivenCommandResourceWhenExecuteCommandThenThrowsError() {
        JSONObject input = new JSONObject(
                "{" +
                        "ust: {" +
                        "       members: [" +
                        "           {class: Name}" +
                        "       ]" +
                        "   }" +
                        "}"
        );
        this.webTestClient.post()
                .uri(CommandResource.COMMAND)
                .bodyValue(new ObjectMapper().readValue(input.toString(), Map.class))
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @SneakyThrows
    @Test
    void testGivenCommandResourceWhenExecuteCommandThenThrowsMemberError() {
        JSONObject input = new JSONObject(
                "{" +
                        "add: {" +
                        "       members: [" +
                        "           {ust: Name}" +
                        "       ]" +
                        "   }" +
                        "}"
        );
        this.webTestClient.post()
                .uri(CommandResource.COMMAND)
                .bodyValue(new ObjectMapper().readValue(input.toString(), Map.class))
                .exchange()
                .expectStatus().is4xxClientError();
    }

}
