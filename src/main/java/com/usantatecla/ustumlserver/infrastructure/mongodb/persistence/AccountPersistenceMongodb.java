package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.domain.persistence.AccountPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.AccountDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AccountEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountPersistenceMongodb extends MemberPersistenceMongodb implements AccountPersistence {

    private AccountDao accountDao;
    private MemberEntityUpdater memberEntityUpdater;
    private MemberEntityDeleter memberEntityDeleter;

    @Autowired
    public AccountPersistenceMongodb(AccountDao accountDao, MemberEntityUpdater memberEntityUpdater
            , MemberEntityDeleter memberEntityDeleter) {
        this.accountDao = accountDao;
        this.memberEntityUpdater = memberEntityUpdater;
        this.memberEntityDeleter = memberEntityDeleter;
    }

    @Override
    public void create(Account account) {
        if (accountDao.findByEmail(account.getEmail()) != null) {
            throw new PersistenceException(ErrorMessage.EMAIL_ALREADY_EXISTS, account.getEmail());
        }
        this.accountDao.save(new AccountEntity(account));
    }

    @Override
    public Account read(String email) {
        AccountEntity accountEntity = this.accountDao.findByEmail(email);
        if (accountEntity == null) {
            throw new PersistenceException(ErrorMessage.USER_NOT_FOUND, email);
        }
        return accountEntity.toAccount();
    }

    @Override
    public Account update(Account account) {
        return ((AccountEntity) this.memberEntityUpdater.update(account)).toAccount();
    }

    @Override
    public Account deleteMembers(Account account, List<Member> members) {
        return ((AccountEntity) this.memberEntityDeleter.delete(account, members)).toAccount();
    }

    @Override
    public Member deleteRelations(Member member, List<Relation> relations) {
        this.deleteRelations(relations);
        return this.read(((Account) member).getEmail());
    }
}
