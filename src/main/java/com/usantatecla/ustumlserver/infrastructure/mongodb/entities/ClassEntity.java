package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Document
public class ClassEntity extends MemberEntity {

    private List<Modifier> modifiers;
    private List<AttributeEntity> attributesEntities;
    private List<MethodEntity> methodsEntities;

    public ClassEntity(Class clazz) {
        super(clazz.getId(), clazz.getName());
        BeanUtils.copyProperties(clazz, this);
        this.attributesEntities = new ArrayList<>();
        this.methodsEntities = new ArrayList<>();
        for (Attribute attribute: clazz.getAttributes()) {
           this.attributesEntities.add(new AttributeEntity(attribute));
        }
        for (Method method: clazz.getMethods()) {
            this.methodsEntities.add(new MethodEntity(method));
        }
    }

    @Override
    protected Member toMember() {
        return this.toClass();
    }

    public Class toClass() {
        Class clazz = new Class();
        BeanUtils.copyProperties(this, clazz);
        clazz.setAttributes(this.getAttributes());
        clazz.setMethods(this.getMethods());
        clazz.setRelations(this.getRelations());
        return clazz;
    }

    private List<Attribute> getAttributes() {
        List<Attribute> attributes = new ArrayList<>();
        if (Objects.nonNull(this.attributesEntities)) {
            for (AttributeEntity attributeEntity: this.attributesEntities){
                attributes.add(attributeEntity.toAttribute());
            }
        }
        return attributes;
    }

    private List<Method> getMethods() {
        List<Method> methods = new ArrayList<>();
        if (Objects.nonNull(this.methodsEntities)) {
            for (MethodEntity methodEntity: this.methodsEntities){
                methods.add(methodEntity.toMethod());
            }
        }
        return methods;
    }


}
