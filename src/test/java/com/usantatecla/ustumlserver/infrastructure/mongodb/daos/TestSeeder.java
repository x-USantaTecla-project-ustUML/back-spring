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

@Repository
public class TestSeeder {

    public static String SESSION_ID = "sessionid";
    public static String CLASS_NAME = "class";
    public static Class CLASS = new ClassBuilder().name(TestSeeder.CLASS_NAME).build();
    public static String PACKAGE_NAME = "package";
    public static Package PACKAGE = new PackageBuilder().name(TestSeeder.PACKAGE_NAME).build();

    private Seeder seeder;
    private PackageDao packageDao;
    private ClassDao classDao;
    private UserDao userDao;
    private SessionDao sessionDao;

    @Autowired
    public TestSeeder(Seeder seeder, PackageDao packageDao, ClassDao classDao, UserDao userDao, SessionDao sessionDao) {
        this.seeder = seeder;
        this.packageDao = packageDao;
        this.classDao = classDao;
        this.userDao = userDao;
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
        PackageEntity mainPackage = this.packageDao.findByName("name");
        List<MemberEntity> memberEntities = List.of(packageEntity, classEntity);
        mainPackage.setMemberEntities(memberEntities);
        this.packageDao.save(mainPackage);
        SessionEntity openSession = SessionEntity.builder()
                .sessionId(TestSeeder.SESSION_ID)
                .memberEntities(List.of(mainPackage))
                .build();
        this.sessionDao.save(openSession);
    }

    public void seedClose() {
        this.seedOpen();
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(TestSeeder.SESSION_ID);
        List<MemberEntity> memberEntities = sessionEntity.getMemberEntities();
        memberEntities.add(this.packageDao.findByName(TestSeeder.PACKAGE_NAME));
        sessionEntity.setMemberEntities(memberEntities);
        this.sessionDao.save(sessionEntity);
    }

}
