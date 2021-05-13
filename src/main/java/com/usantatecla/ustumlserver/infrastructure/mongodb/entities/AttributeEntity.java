package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Attribute;
import com.usantatecla.ustumlserver.domain.model.Modifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AttributeEntity {

    private String name;
    private String type;
    private List<Modifier> modifiers;

    public AttributeEntity(Attribute attribute) {
        BeanUtils.copyProperties(attribute, this);
    }

    public Attribute toAttribute() {
        Attribute attribute = new Attribute();
        BeanUtils.copyProperties(this, attribute);
        return attribute;
    }

}
