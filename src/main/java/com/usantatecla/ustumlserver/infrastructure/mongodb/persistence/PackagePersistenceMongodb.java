package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PackagePersistenceMongodb implements PackagePersistence {

    private PackageDao packageDao;
    private MemberEntityUpdater memberEntityUpdater;
    private MemberEntityDeleter memberEntityDeleter;

    @Autowired
    public PackagePersistenceMongodb(PackageDao packageDao, MemberEntityUpdater memberEntityUpdater
            , MemberEntityDeleter memberEntityDeleter) {
        this.packageDao = packageDao;
        this.memberEntityUpdater = memberEntityUpdater;
        this.memberEntityDeleter = memberEntityDeleter;
    }

    @Override
    public Package read(String id) {
        Optional<PackageEntity> packageEntity = this.packageDao.findById(id);
        if (packageEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, id);
        }
        return packageEntity.get().toPackage();
    }

    @Override
    public Package update(Package pakage) {
        return ((PackageEntity) this.memberEntityUpdater.update(pakage)).toPackage();
    }

    @Override
    public Package delete(Package pakage, List<Member> members, List<Relation> relations) {
        return ((PackageEntity) this.memberEntityDeleter.delete(pakage, members, relations)).toPackage();
    }

}
