package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Project extends Package {

    private static final String UML_NAME = "project";

    public Project(String name, List<Member> members) {
        super(name, members);
    }

    @Override
    public String getUstName() {
        return Project.UML_NAME + ":";
    }

}
