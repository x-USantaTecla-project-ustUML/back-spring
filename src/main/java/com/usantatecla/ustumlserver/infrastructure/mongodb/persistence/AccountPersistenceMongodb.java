package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.AccountPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.AccountDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AccountEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.MemberEntityDeleter;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.MemberEntityUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountPersistenceMongodb implements AccountPersistence {

    private AccountDao accountDao;
    private MemberEntityDeleter memberEntityDeleter;
    private MemberEntityUpdater memberEntityUpdater;

    @Autowired
    public AccountPersistenceMongodb(AccountDao accountDao, MemberEntityDeleter memberEntityDeleter, MemberEntityUpdater memberEntityUpdater) {
        this.accountDao = accountDao;
        this.memberEntityDeleter = memberEntityDeleter;
        this.memberEntityUpdater = memberEntityUpdater;
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
    public Account deleteMembers(Account account, List<Member> members) {
        for (Member member : members) {
            this.memberEntityDeleter.delete(member);
        }
        return (Account) this.memberEntityUpdater.update(account).toMember();
    }

}
