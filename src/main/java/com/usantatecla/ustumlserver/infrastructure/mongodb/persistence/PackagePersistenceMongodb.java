package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Data
@Repository
public class PackagePersistenceMongodb implements PackagePersistence {

    private PackageDao packageDao;
    private ClassDao classDao;
    private Stack<MemberEntity> memberEntities;

    @Autowired
    public PackagePersistenceMongodb(PackageDao packageDao, ClassDao classDao) {
        this.packageDao = packageDao;
        this.classDao = classDao;
        this.memberEntities = new Stack<>();
    }

    @Override
    public Package read(String id) {
        return this.find(id).toPackage();
    }

    private PackageEntity find(String id) {
        Optional<PackageEntity> packageEntity = this.packageDao.findById(id);
        if (packageEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, id);
        }
        return packageEntity.get();
    }

    @Override
    public void update(Package pakage) {
        PackageEntity packageEntity = this.find(pakage.getId());
        PackageMembersUpdater packageMembersUpdater = new PackageMembersUpdater(this);
        packageEntity.setMemberEntities(packageMembersUpdater.update(pakage.getMembers()));
        this.packageDao.save(packageEntity);
    }

    class PackageMembersUpdater implements MemberVisitor {

        private PackagePersistenceMongodb packagePersistence;
        private Stack<MemberEntity> memberEntities;

        PackageMembersUpdater(PackagePersistenceMongodb packagePersistence) {
            this.packagePersistence = packagePersistence;
            this.memberEntities = this.packagePersistence.getMemberEntities();
        }

        List<MemberEntity> update(List<Member> members) {
            for (Member member : members) {
                member.accept(this);
            }
            return new ArrayList<>(this.memberEntities);
        }

        @Override
        public void visit(Project project) {
            //TODO
        }

        @Override
        public void visit(Package pakage) {
            PackageEntity packageEntity = new PackageEntity(pakage);
            if (pakage.getId() != null) {
                packageEntity = this.packagePersistence.find(pakage.getId());
            }
            this.memberEntities.add(packageEntity);
            for (Member member : pakage.getMembers()) {
                member.accept(this);
            }
            MemberEntity memberEntity = this.memberEntities.peek();
            while (!packageEntity.equals(memberEntity)) {
                packageEntity.add(this.memberEntities.pop());
                memberEntity = this.memberEntities.peek();
            }
            this.packagePersistence.getPackageDao().save(packageEntity);
        }

        @Override
        public void visit(Class clazz) {
            if (clazz.getId() == null) {
                this.createClassEntity(clazz);
            } else {
                Optional<ClassEntity> optionalClassEntity = this.packagePersistence.getClassDao().findById(clazz.getId());
                if (optionalClassEntity.isEmpty()) {
                    this.createClassEntity(clazz);
                } else {
                    this.memberEntities.add(optionalClassEntity.get());
                }
            }
        }

        private void createClassEntity(Class clazz) {
            ClassEntity classEntity = new ClassEntity(clazz);
            assert classEntity == this.packagePersistence.getClassDao().save(classEntity);
            this.memberEntities.add(classEntity);
        }

    }

}
