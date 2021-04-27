package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ParameterTest {

    @Test
    public void testGivenParameterWhenGetUSTUMLThenReturn() {
        assertThat(new Parameter("param", "char").getUSTUML(), is("char param"));
        assertThat(new Parameter("param", "Game").getUSTUML(), is("Game param"));
    }

    @Test
    public void testGivenParameterWhenGetPlantUMLThenReturn() {
        assertThat(new Parameter("param", "char").getPlantUML(), is("char param"));
        assertThat(new Parameter("param", "Game").getPlantUML(), is("Game param"));
    }

}
