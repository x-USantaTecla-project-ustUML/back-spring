package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.PackageBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.SessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TestSeeder {

    public static String INSIDE_PACKAGE_ID = "package123ID";
    public static String SESSION_ID = "sessionid";
    public static String CLASS_NAME = "class";
    public static Class CLASS = new ClassBuilder().id("class123ID").name(TestSeeder.CLASS_NAME).build();
    public static String PACKAGE_NAME = "package";
    public static Package PACKAGE = new PackageBuilder().id(TestSeeder.INSIDE_PACKAGE_ID).name(TestSeeder.PACKAGE_NAME).build();

    private Seeder seeder;
    private PackageDao packageDao;
    private ClassDao classDao;
    private AccountDao accountDao;
    private SessionDao sessionDao;

    @Autowired
    public TestSeeder(Seeder seeder, PackageDao packageDao, ClassDao classDao, AccountDao accountDao, SessionDao sessionDao) {
        this.seeder = seeder;
        this.packageDao = packageDao;
        this.classDao = classDao;
        this.accountDao = accountDao;
        this.sessionDao = sessionDao;
        this.initialize();
    }

    public void initialize() {
        this.seeder.initialize();
    }

    public void seedOpen() {
        ClassEntity classEntity = new ClassEntity(TestSeeder.CLASS);
        this.classDao.save(classEntity);
        PackageEntity packageEntity = new PackageEntity(TestSeeder.PACKAGE);
        this.packageDao.save(packageEntity);
        Optional<PackageEntity> mainPackageDB = this.packageDao.findById(Seeder.PROJECT_ID);
        if (mainPackageDB.isPresent()) {
            PackageEntity mainPackage = mainPackageDB.get();
            List<MemberEntity> memberEntities = List.of(packageEntity, classEntity);
            mainPackage.setMemberEntities(memberEntities);
            this.packageDao.save(mainPackage);
            SessionEntity openSession = SessionEntity.builder()
                    .sessionId(TestSeeder.SESSION_ID)
                    .memberEntities(List.of(mainPackage))
                    .build();
            this.sessionDao.save(openSession);
        }
    }

    public void seedClose() {
        this.seedOpen();
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(TestSeeder.SESSION_ID);
        List<MemberEntity> memberEntities = sessionEntity.getMemberEntities();
        Optional<PackageEntity> insidePackageDB = this.packageDao.findById(TestSeeder.INSIDE_PACKAGE_ID);
        insidePackageDB.ifPresent(memberEntities::add);
        sessionEntity.setMemberEntities(memberEntities);
        this.sessionDao.save(sessionEntity);
    }

}
