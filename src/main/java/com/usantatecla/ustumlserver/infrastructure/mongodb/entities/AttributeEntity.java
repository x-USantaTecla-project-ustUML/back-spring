package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Attribute;
import com.usantatecla.ustumlserver.domain.model.Modifier;
import com.usantatecla.ustumlserver.domain.model.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class AttributeEntity {
    @Id
    private String id;
    private String name;
    private String type;
    private List<Modifier> modifiers;

    public Attribute toAttribute() {
        Attribute attribute = new Attribute();
        BeanUtils.copyProperties(this, attribute);
        return attribute;
    }

}
