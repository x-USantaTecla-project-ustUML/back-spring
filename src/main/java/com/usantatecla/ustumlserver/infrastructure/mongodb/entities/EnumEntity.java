package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Enum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@Document
public class EnumEntity extends ClassEntity {

    private List<String> objects;

    public EnumEntity(Enum _enum) {
        super(_enum);
        this.objects = _enum.getObjects();
    }

    @Override
    protected Class createClass() {
        Enum _enum = new Enum();
        _enum.setObjects(this.objects);
        return _enum;
    }

}
