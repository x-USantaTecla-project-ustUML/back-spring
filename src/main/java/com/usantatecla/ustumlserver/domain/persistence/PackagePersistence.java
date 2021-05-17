package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Package;
import org.springframework.stereotype.Repository;

@Repository
public interface PackagePersistence {
    Package read(String name);

    void update(Package pakage);

}
