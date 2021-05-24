package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PackageDao extends MongoRepository<PackageEntity, String> {
    Optional<PackageEntity> findById(String id);

    PackageEntity findByName(String name);

    MemberEntity findMemberByName(String name);

}
