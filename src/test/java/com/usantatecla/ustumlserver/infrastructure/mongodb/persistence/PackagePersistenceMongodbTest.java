package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.PackageBuilder;
import com.usantatecla.ustumlserver.domain.services.CommandParserException;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
public class PackagePersistenceMongodbTest {

    private static final String EXIST_NAME = "name";
    private static final String NON_EXIST_NAME = "eman";

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
        Package expected = new PackageBuilder().build();
        assertThat(this.packagePersistence.read(PackagePersistenceMongodbTest.EXIST_NAME), is(expected));
    }

    @Test
    void testGivenPackagePersistenceWhenReadThenError() {
        assertThrows(CommandParserException.class, () -> this.packagePersistence.read(PackagePersistenceMongodbTest.NON_EXIST_NAME));
    }

    @Test
    void testGivenPackagePersistenceWhenUpdateThenReturn() {
        this.packagePersistence.update(new PackageBuilder()
                .clazz().name(PackagePersistenceMongodbTest.EXIST_NAME).build());
        assertThat(this.packagePersistence.read(PackagePersistenceMongodbTest.EXIST_NAME).getMembers().size(), is(1));
    }

    @Test
    void testGivenPackagePersistenceWhenUpdateThenError() {
        Package pakage = new PackageBuilder().name(PackagePersistenceMongodbTest.NON_EXIST_NAME).build();
        assertThrows(CommandParserException.class, () -> this.packagePersistence.update(pakage));
    }

}
