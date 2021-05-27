package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.AccountPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.AccountDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AccountEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Repository
public class AccountPersistenceMongodb implements AccountPersistence {

    private AccountDao accountDao;
    private PackageUpdater packageUpdater;

    @Autowired
    public AccountPersistenceMongodb(AccountDao accountDao, PackageUpdater packageUpdater) {
        this.accountDao = accountDao;
        this.packageUpdater = packageUpdater;
    }

    @Override
    public void create(Account account) {
        if (accountDao.findByEmail(account.getEmail()) != null) {
            throw new PersistenceException(ErrorMessage.EMAIL_ALREADY_EXISTS, account.getEmail());
        }
        this.accountDao.save(new AccountEntity(account));
    }

    protected AccountEntity find(String email) {
        AccountEntity accountEntity = this.accountDao.findByEmail(email);
        if (accountEntity == null) {
            throw new PersistenceException(ErrorMessage.USER_NOT_FOUND, email);
        }
        return accountEntity;
    }

    @Override
    public void update(Account account) {
        AccountEntity accountEntity = this.find(account.getEmail());
        List<PackageEntity> packageEntities = new ArrayList<>();
        for(Project project: account.getProjects()) {
            packageEntities.add(this.packageUpdater.update(project));
        }
        accountEntity.setPackageEntities(packageEntities);
        this.accountDao.save(accountEntity);
    }

    @Override
    public Account read(String email) {
        return this.find(email).toAccount();
    }

}
