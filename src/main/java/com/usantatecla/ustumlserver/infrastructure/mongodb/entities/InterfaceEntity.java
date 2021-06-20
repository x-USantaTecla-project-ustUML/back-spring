package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Interface;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@SuperBuilder
@Document
public class InterfaceEntity extends ClassEntity {

    public InterfaceEntity(Interface _interface) {
        super(_interface);
    }

    @Override
    protected Class createClass() {
        return new Interface();
    }
}
