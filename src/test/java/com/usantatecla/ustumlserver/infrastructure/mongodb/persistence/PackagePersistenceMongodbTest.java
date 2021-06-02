package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class PackagePersistenceMongodbTest {

    private static final String NON_EXIST_ID = "NonExisT123";

    @Autowired
    private PackagePersistenceMongodb packagePersistence;
    @Autowired
    private Seeder seeder;

    @BeforeEach
    void beforeEach() {
        this.seeder.initialize();
    }

    @Test
    void testGivenPackagePersistenceWhenReadThenReturn() {
        Package expected = new PackageBuilder().id(Seeder.PROJECT_ID).build();
        assertThat(this.packagePersistence.read(Seeder.PROJECT_ID), is(expected));
    }

    @Test
    void testGivenPackagePersistenceWhenReadThenError() {
        assertThrows(PersistenceException.class, () -> this.packagePersistence.read(PackagePersistenceMongodbTest.NON_EXIST_ID));
    }

    @Test
    void testGivenPackagePersistenceWhenUpdateThenReturn() {
        this.packagePersistence.update(new PackageBuilder().id(Seeder.PROJECT_ID)
                .clazz().name("newClass").build());
        assertThat(this.packagePersistence.read(Seeder.PROJECT_ID).getMembers().size(), is(1));
    }

    @Test
    void testGivenPackagePersistenceWhenUpdateThenError() {
        Package pakage = new PackageBuilder().id(PackagePersistenceMongodbTest.NON_EXIST_ID).build();
        assertThrows(PersistenceException.class, () -> this.packagePersistence.update(pakage));
    }

}
