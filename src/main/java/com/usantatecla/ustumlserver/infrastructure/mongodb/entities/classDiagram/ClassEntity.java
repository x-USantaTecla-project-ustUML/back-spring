package com.usantatecla.ustumlserver.infrastructure.mongodb.entities.classDiagram;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Attribute;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Method;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Modifier;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
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
        BeanUtils.copyProperties(clazz, this);
        this.attributesEntities = new ArrayList<>();
        this.methodsEntities = new ArrayList<>();
        for (Attribute attribute : clazz.getAttributes()) {
            this.attributesEntities.add(new AttributeEntity(attribute));
        }
        for (Method method : clazz.getMethods()) {
            this.methodsEntities.add(new MethodEntity(method));
        }
    }

    @Override
    public Member toMember() {
        return this.toClass();
    }

    @Override
    public Member toMemberWithoutRelations() {
        Class clazz = this.createClass();
        BeanUtils.copyProperties(this, clazz);
        clazz.setAttributes(this.getAttributes());
        clazz.setMethods(this.getMethods());
        clazz.setRelations(new ArrayList<>());
        return clazz;
    }

    protected Class createClass() {
        return new Class();
    }

    public Class toClass() {
        Class clazz = (Class) this.toMemberWithoutRelations();
        clazz.setRelations(this.getRelations());
        return clazz;
    }

    private List<Attribute> getAttributes() {
        List<Attribute> attributes = new ArrayList<>();
        if (Objects.nonNull(this.attributesEntities)) {
            for (AttributeEntity attributeEntity : this.attributesEntities) {
                attributes.add(attributeEntity.toAttribute());
            }
        }
        return attributes;
    }

    private List<Method> getMethods() {
        List<Method> methods = new ArrayList<>();
        if (Objects.nonNull(this.methodsEntities)) {
            for (MethodEntity methodEntity : this.methodsEntities) {
                methods.add(methodEntity.toMethod());
            }
        }
        return methods;
    }


}
