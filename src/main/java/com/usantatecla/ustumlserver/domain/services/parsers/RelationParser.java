package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class RelationParser {

    protected String targetName;
    protected String role;

    public abstract RelationParser copy();

    public abstract Relation get(Command relationCommand);
}
