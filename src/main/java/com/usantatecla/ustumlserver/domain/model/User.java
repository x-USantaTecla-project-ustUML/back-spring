package com.usantatecla.ustumlserver.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
public class User {
    private String email;
    private String password;
    private Role role;
    private List<Project> projects;
}
