package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.TestConfig;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestConfig
public class AccountTest {

    private static final String NAME = "name";

    @Test
    void testGivenAnAccountWhenFindThenReturn() {
        Account account = Account.builder()
                .projects(Collections.singletonList(Project.builder().name(AccountTest.NAME).build()))
                .build();
        assertThat(account.find(AccountTest.NAME), is(Project.builder().name(AccountTest.NAME).build()));
    }

    @Test
    void testGivenAnAccountWhenGetUstNameThenReturn() {
        assertThat(new Account().getUstName(), is("account:"));
    }

}
