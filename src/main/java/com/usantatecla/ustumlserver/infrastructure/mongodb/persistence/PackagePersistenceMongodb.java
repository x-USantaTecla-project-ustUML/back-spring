package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.MemberEntityDeleter;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.MemberEntityUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PackagePersistenceMongodb implements PackagePersistence {

    private PackageDao packageDao;
    private MemberEntityDeleter memberEntityDeleter;
    private MemberEntityUpdater memberEntityUpdater;

    @Autowired
    public PackagePersistenceMongodb(PackageDao packageDao, MemberEntityDeleter memberEntityDeleter, MemberEntityUpdater memberEntityUpdater) {
        this.packageDao = packageDao;
        this.memberEntityDeleter = memberEntityDeleter;
        this.memberEntityUpdater = memberEntityUpdater;
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
    public Package deleteMembers(Package pakage, List<Member> members) {
        for (Member member : members) {
            this.memberEntityDeleter.delete(member);
        }
        return (Package) this.memberEntityUpdater.update(pakage).toMember();
    }

}
