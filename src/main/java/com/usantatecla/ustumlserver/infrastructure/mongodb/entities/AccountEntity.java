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
public class AccountEntity extends MemberEntity{

    @Indexed(unique = true)
    private String email;
    private String password;
    private Role role;
    @DBRef(lazy = true)
    private List<PackageEntity> packageEntities;

    public AccountEntity(Account account) {
        super(account.getId(), account.getName());
        BeanUtils.copyProperties(account, this);
        this.packageEntities = new ArrayList<>();
        for (Project project: account.getProjects()) {
            this.packageEntities.add(new PackageEntity(project));
        }
    }

    public void addProject(PackageEntity packageEntity) {
        if (this.packageEntities == null) {
            this.packageEntities = new ArrayList<>();
        }
        this.packageEntities.add(packageEntity);
    }

    public Account toAccount() {
        Account account = new Account();
        BeanUtils.copyProperties(this, account);
        List<Project> projects = new ArrayList<>();
        if (Objects.nonNull(this.getPackageEntities())) {
            for (PackageEntity packageEntity : this.getPackageEntities()) {
                projects.add(packageEntity.toProject());
            }
        }
        account.setProjects(projects);
        return account;
    }

    @Override
    protected Member toMember() {
        return this.toAccount();
    }
}
