package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Data
@Repository
public class PackagePersistenceMongodb implements PackagePersistence {

    private PackageDao packageDao;
    private PackageUpdater packageUpdater;

    @Autowired
    public PackagePersistenceMongodb(PackageDao packageDao, PackageUpdater packageUpdater) {
        this.packageDao = packageDao;
        this.packageUpdater = packageUpdater;
    }

    @Override
    public Package read(String id) {
        return this.packageUpdater.find(id).toPackage();
    }

    @Override
    public void update(Package pakage) {
        this.packageUpdater.update(pakage);
    }

}
