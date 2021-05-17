package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.domain.model.RelationBuilder;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RelationServiceTest {

    @Test
    void testGivenRelationServiceWhenAddSimpleUseThenReturn() {
        String target = "a";
        Command command = new CommandBuilder().command("{" +
                "   use: " + target +
                "}").build();
        Relation expected = new RelationBuilder().use().target(target).build();
        assertThat(new RelationService().parseRelation(command), is(expected));
    }

    @Test
    void testGivenRelationServiceWhenAddCompleteUseThenReturn() {
        String target = "a";
        String role = "role";
        Command command = new CommandBuilder().command("{" +
                "   use: " + target + "," +
                "rol: " + role +
                "}").build();
        Relation expected = new RelationBuilder().use().target(target).role(role).build();
        assertThat(new RelationService().parseRelation(command), is(expected));
    }

}
