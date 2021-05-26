package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Project extends Package{

    public static final String UST_NAME = "project:";

    public Project(String name, List<Member> members) {
        super(name, members);
    }

    @Override
    public String getUstName() {
        return Project.UST_NAME;
    }

}
