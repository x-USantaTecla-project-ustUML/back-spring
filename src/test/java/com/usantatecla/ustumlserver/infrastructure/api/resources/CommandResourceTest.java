package com.usantatecla.ustumlserver.infrastructure.api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandResponseDto;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.TestSeeder;
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
class CommandResourceTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RestClientTestService restClientTestService;

    @Autowired
    private TestSeeder seeder;

    @BeforeEach
    void beforeEach() {
        this.seeder.initialize();
    }

    @SneakyThrows
    @Test
    void testGivenCommandResourceWhenExecuteCommandNotLoggedThenThrowsUnauthorized() {
        JSONObject input = new JSONObject(
                "{" +
                        "add: {}" +
                        "}"
        );
        this.webTestClient.post()
                .uri(CommandResource.COMMAND)
                .header("Authorization", "a")
                .bodyValue(new ObjectMapper().readValue(input.toString(), Map.class))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @SneakyThrows
    @Test
    void testGivenCommandResourceWhenExecuteCommandThenReturn() {
        JSONObject input = new JSONObject(
                "{" +
                        "add: {" +
                        "       members: [" +
                        "           {project: Name}" +
                        "       ]" +
                        "   }" +
                        "}"
        );
        CommandResponseDto expected = new CommandResponseDto(
                "package Name {\n" +
                        "    }",
                        "members:\n" +
                        "  - project: Name",""); //TODO
        this.restClientTestService.login(this.webTestClient).post()
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
                        "   members: [" +
                        "     {" +
                        "       project: Project," +
                        "       members: [" +
                        "           {" +
                        "               package: otherPackage," +
                        "               members: [" +
                        "                   {class: InsidePackage}" +
                        "               ]" +
                        "           }," +
                        "           {class: Name}" +
                        "       ]" +
                        "      }" +
                        "     ]" +
                        "   }" +
                        "}"
        );
        CommandResponseDto expected = new CommandResponseDto(
                "package Project {\n" +
                        "    }",
                "members:\n" +
                        "  - project: Project", ""); //TODO

        this.restClientTestService.login(this.webTestClient).post()
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
        this.restClientTestService.login(this.webTestClient).post()
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
        this.restClientTestService.login(this.webTestClient).post()
                .uri(CommandResource.COMMAND)
                .bodyValue(new ObjectMapper().readValue(input.toString(), Map.class))
                .exchange()
                .expectStatus().is4xxClientError();
    }

}
