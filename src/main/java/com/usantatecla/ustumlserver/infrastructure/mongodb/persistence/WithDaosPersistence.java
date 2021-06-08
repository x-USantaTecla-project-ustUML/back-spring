package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.*;
import org.springframework.beans.factory.annotation.Autowired;

abstract class WithDaosPersistence {

    protected AccountDao accountDao;
    protected PackageDao packageDao;
    protected ProjectDao projectDao;
    protected ClassDao classDao;
    protected InterfaceDao interfaceDao;

    @Autowired
    WithDaosPersistence(AccountDao accountDao, PackageDao packageDao, ProjectDao projectDao, ClassDao classDao, InterfaceDao interfaceDao) {
        this.accountDao = accountDao;
        this.packageDao = packageDao;
        this.projectDao = projectDao;
        this.classDao = classDao;
        this.interfaceDao = interfaceDao;
    }
}
