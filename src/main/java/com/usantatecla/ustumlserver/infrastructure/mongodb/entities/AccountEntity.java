package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class AccountEntity extends MemberEntity {

    @Indexed(unique = true)
    private String email;
    private String password;
    private Role role;
    @DBRef(lazy = true)
    private List<ProjectEntity> projectEntities;

    public AccountEntity(Account account) {
        BeanUtils.copyProperties(account, this);
        this.projectEntities = new ArrayList<>();
        for (Project project : account.getProjects()) {
            this.projectEntities.add(new ProjectEntity(project));
        }
    }

    public Account toAccount() {
        Account account = new Account();
        BeanUtils.copyProperties(this, account);
        List<Project> projects = new ArrayList<>();
        if (Objects.nonNull(this.getProjectEntities())) {
            for (ProjectEntity projectEntity : this.getProjectEntities()) {
                if (Objects.nonNull(projectEntity)) {
                    projects.add(projectEntity.toProject());
                }
            }
        }
        account.setProjects(projects);
        return account;
    }

    @Override
    protected Member toMember() {
        return this.toAccount();
    }

    @Override
    public Member toMemberWithoutRelations() {
        return this.toAccount();
    }
}
