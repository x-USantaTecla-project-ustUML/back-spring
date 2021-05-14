package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.ClassBuilder;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ClassEntityTest {

    @Test
    void testGivenClassEntityWhenToClassThenReturn() {
        Class expected = new ClassBuilder().name("class")._public()
                .attribute()._final()
                .method()._abstract().parameter()
                .build();
        ClassEntity classEntity = new ClassEntity(expected);
        assertThat(classEntity.toClass(), is(expected));
    }

}
