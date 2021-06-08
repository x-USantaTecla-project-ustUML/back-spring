package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@SuperBuilder
@Document
public class ProjectEntity extends PackageEntity {

    public ProjectEntity(Project project) {
        super(project);
    }

    @Override
    protected Package createPackage() {
        return new Project();
    }
}
