package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Class;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class ClassEntity extends MemberEntity {
    private List<Modifier> modifiers;
    @DBRef(lazy = true)
    private List<AttributeEntity> attributesEntities;
    @DBRef(lazy = true)
    private List<MethodEntity> methodsEntities;

    public Class toClass() {
        Class clazz = new Class();
        BeanUtils.copyProperties(this, clazz);
        clazz.setAttributes(this.getAttributes());
        clazz.setMethods(this.getMethods());
        return clazz;
    }

    private List<Attribute> getAttributes() {
        List<Attribute> attributes = new ArrayList<>();
        if (Objects.nonNull(this.getAttributesEntities())) {
            for (AttributeEntity attributeEntity: this.getAttributesEntities()){
                attributes.add(attributeEntity.toAttribute());
            }
        }
        return attributes;
    }

    private List<Method> getMethods() {
        List<Method> methods = new ArrayList<>();
        if (Objects.nonNull(this.getMethodsEntities())) {
            for (MethodEntity methodEntity: this.getMethodsEntities()){
                methods.add(methodEntity.toMethod());
            }
        }
        return methods;
    }

    @Override
    protected Member toMember() {
        return null;
    }

}
