package com.usantatecla.ustumlserver.infrastructure.api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usantatecla.ustumlserver.domain.services.SessionService;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.TestSeeder;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

@RestTestConfig
class CommandResourceTest {

    @Mock
    private SessionService sessionService;

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
