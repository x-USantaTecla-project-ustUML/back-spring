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
public class MethodEntity {
    @Id
    private String id;
    private String name;
    private String type;
    private List<Modifier> modifiers;
    @DBRef(lazy = true)
    private List<ParameterEntity> parametersEntities;

    public Method toMethod() {
        Method method = new Method();
        BeanUtils.copyProperties(this, method);
        if (Objects.nonNull(this.getParametersEntities())) {
            List<Parameter> parameters = new ArrayList<>();
            for (ParameterEntity parameterEntity: this.getParametersEntities()){
                parameters.add(parameterEntity.toParameter());
            }
            method.setParameters(parameters);
        }
        return method;
    }

}
