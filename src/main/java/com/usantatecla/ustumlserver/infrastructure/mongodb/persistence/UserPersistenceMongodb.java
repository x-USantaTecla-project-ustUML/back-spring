package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.User;
import com.usantatecla.ustumlserver.domain.persistence.UserPersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.CommandParserException;
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
    public Error create(User user) {
        if (userDao.findByEmail(user.getEmail()) != null) {
            return Error.EMAIL_ALREADY_EXISTS;
        }
        this.save(user);
        return Error.NULL;
    }

    @Override
    public User read(String email) {
        return this.userDao.findByEmail(email).toUser();
    }

    @Override
    public Error exist(String email) {
        return (this.read(email) != null)? Error.NULL:Error.USER_NOT_FOUND;
    }

    @Override
    public void save(User user) {
        UserEntity userEntity = new UserEntity(user);
        this.userDao.save(userEntity);
    }
}
