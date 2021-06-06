package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.persistence.AccountPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.AccountDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountPersistenceMongodb implements AccountPersistence {

    private AccountDao accountDao;
    private MemberEntityUpdater memberEntityUpdater;

    @Autowired
    public AccountPersistenceMongodb(AccountDao accountDao, MemberEntityUpdater memberEntityUpdater) {
        this.accountDao = accountDao;
        this.memberEntityUpdater = memberEntityUpdater;
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
        this.memberEntityUpdater.update(account);
    }

    @Override
    public Account read(String email) {
        return this.find(email).toAccount();
    }

}
