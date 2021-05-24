package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.User;
import com.usantatecla.ustumlserver.domain.persistence.UserPersistence;
import com.usantatecla.ustumlserver.domain.services.Error;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.UserDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserPersistenceMongodb implements UserPersistence {

    private UserDao userDao;

    @Autowired
    public UserPersistenceMongodb(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void create(User user) {
        if (userDao.findByEmail(user.getEmail()) != null) {
            throw new PersistenceException(Error.EMAIL_ALREADY_EXISTS, user.getEmail());
        }
        this.userDao.save(new UserEntity(user));
    }

    @Override
    public User read(String email) {
        UserEntity userEntity = this.userDao.findByEmail(email);
        if (userEntity == null) {
            throw new PersistenceException(Error.USER_NOT_FOUND, email);
        }
        return userEntity.toUser();
    }

}
