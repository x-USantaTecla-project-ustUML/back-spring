package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Modifier;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.PackageBuilder;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestConfig
public class PackageEntityTest {

    @Test
    void testGivenPackageEntityWhenToPackageThenReturn() {
        PackageEntity input = PackageEntity.builder()
                .name("name")
                .memberEntities(Collections.singletonList(ClassEntity.builder().modifiers(Collections.singletonList(Modifier.PACKAGE)).name("Class").build()))
                .build();
        Package expected = new PackageBuilder().clazz().name("Class").build();
        assertThat(input.toPackage(), is(expected));
    }

}
