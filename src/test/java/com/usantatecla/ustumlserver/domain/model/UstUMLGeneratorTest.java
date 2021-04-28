package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UstUMLGeneratorTest {

    Generator generatorUstUML = new UstUMLGenerator();

    @Test
    public void testGivenParameterWhenGetUSTUMLThenReturn() {
        new Class("clazz", Arrays.asList(Modifier.ABSTRACT, Modifier.PUBLIC),
                Arrays.asList(new Attribute("attribute", "int", Arrays.asList(Modifier.PUBLIC)))).accept(generatorUstUML);
        assertThat(generatorUstUML.toString(), is("class: clazz\nmodifiers:\n\t- abstract\n\t- public\nmembers:\n\t- definition: public int attribute"));
    }

}
