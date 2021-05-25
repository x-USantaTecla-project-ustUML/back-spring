package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.domain.persistence.ProjectPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ProjectDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Stack;

@Repository
public class ProjectPersistenceMongodb extends PackagePersistenceMongodb {

    private ProjectDao projectDao;

    @Autowired
    public ProjectPersistenceMongodb(ProjectDao projectDao, PackageDao packageDao, ClassDao classDao) {
        super(packageDao, classDao);
        this.projectDao = projectDao;
    }

    @Override
    public Project read(String id) {
        return this.find(id).toProject();
    }

    @Override
    public ProjectEntity find(String id) {
        Optional<ProjectEntity> projectEntity = this.projectDao.findById(id);
        if (projectEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, id);
        }
        return projectEntity.get();
    }

    @Override
    public void update(Project project) {
        ProjectEntity projectEntity = this.find(project.getId());
        for (Member member : project.getMembers()) {
            member.accept(this);
        }
        while (!this.memberEntities.empty()) {
            projectEntity.add(this.memberEntities.pop());
        }
        this.projectDao.save(projectEntity);
    }

    @Override
    public void visit(Project project) {
        // TODO Vacío, nunca se visita a un proyecto dentro de otro
    }

    @Override
    public void visit(Package pakage) {
        PackageEntity packageEntity = new PackageEntity(pakage);
        if (pakage.getId() != null) {
            packageEntity = this.findPackage(pakage.getId());
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
        this.packageDao.save(packageEntity);
    }

    @Override
    public void visit(Class clazz) {
        if (clazz.getId() == null) {
            ClassEntity classEntity = new ClassEntity(clazz);
            assert classEntity == this.classDao.save(classEntity);
            this.memberEntities.add(classEntity);
        } else {
            Optional<ClassEntity> classEntity = this.classDao.findById(clazz.getId());
            if (classEntity.isEmpty()) {
                this.memberEntities.add(this.classDao.save(new ClassEntity(clazz)));
            } else {
                this.memberEntities.add(classEntity.get());
            }
        }
    }
}
