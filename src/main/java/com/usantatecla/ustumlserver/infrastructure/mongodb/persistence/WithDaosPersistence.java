package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.AccountDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.InterfaceDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import org.springframework.beans.factory.annotation.Autowired;

abstract class WithDaosPersistence {

    protected AccountDao accountDao;
    protected PackageDao packageDao;
    protected ClassDao classDao;
    protected InterfaceDao interfaceDao;

    @Autowired
    WithDaosPersistence(AccountDao accountDao, PackageDao packageDao, ClassDao classDao, InterfaceDao interfaceDao) {
        this.accountDao = accountDao;
        this.packageDao = packageDao;
        this.classDao = classDao;
        this.interfaceDao = interfaceDao;
    }
}
