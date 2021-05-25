package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Package;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class UserEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    private String password;
    private Role role;
    @DBRef(lazy = true)
    private List<ProjectEntity> projectEntities;

    public UserEntity(User user) {
        BeanUtils.copyProperties(user, this);
        // TODO User projects to UserEntity projectsEntities
    }

    public void addProject(ProjectEntity projectEntity) {
        if (this.projectEntities == null) {
            this.projectEntities = new ArrayList<>();
        }
        this.projectEntities.add(projectEntity);
    }

    public User toUser() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        List<Project> projects = new ArrayList<>();
        if (Objects.nonNull(this.getProjectEntities())) {
            for (ProjectEntity projectEntity : this.getProjectEntities()) {
                projects.add(projectEntity.toProject());
            }
        }
        user.setProjects(projects);
        return user;
    }

}
