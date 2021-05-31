package com.usantatecla.ustumlserver.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class Relation {

    private Member target;
    private String role;

}
