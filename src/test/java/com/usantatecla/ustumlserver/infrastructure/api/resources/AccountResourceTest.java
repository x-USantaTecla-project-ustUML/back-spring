package com.usantatecla.ustumlserver.infrastructure.api.resources;

import com.usantatecla.ustumlserver.infrastructure.api.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@RestTestConfig
public class AccountResourceTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testGivenUserResourceWhenLoginThenReturn() {
        this.restClientTestService.login(this.webTestClient);
        assertTrue(this.restClientTestService.getToken().length() > 10);
    }

    @Test
    void testGivenUserResourceWhenLoginThenNotFound() {
        webTestClient
                .mutate().filter(basicAuthentication("b", "a")).build()
                .post().uri(UserResource.USERS + UserResource.TOKEN)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testGivenUserResourceWhenRegisterThenReturn() {
        webTestClient.post()
                .uri(UserResource.USERS)
                .bodyValue(UserDto.builder().name("b").email("b").password("b").build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGivenUserResourceWhenRegisterThenAlreadyExists() {
        webTestClient.post()
                .uri(UserResource.USERS)
                .bodyValue(UserDto.builder().email("a").password("pass").build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testGivenUserResourceWhenRegisterNullThenBadRequest() {
        webTestClient.post()
                .uri(UserResource.USERS)
                .bodyValue(UserDto.builder().email(null).password(null).build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testGivenUserResourceWhenRegisterBlankThenBadRequest() {
        webTestClient.post()
                .uri(UserResource.USERS)
                .bodyValue(UserDto.builder().email("").password("").build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testGivenUserResourceWhenLogoutThenReturn() {
        this.restClientTestService.login(this.webTestClient).post()
                .uri(UserResource.USERS + UserResource.LOGOUT)
                .exchange()
                .expectStatus().isOk();
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

}
