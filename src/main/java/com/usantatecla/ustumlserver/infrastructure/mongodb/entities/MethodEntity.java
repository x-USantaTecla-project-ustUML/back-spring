package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Method;
import com.usantatecla.ustumlserver.domain.model.Modifier;
import com.usantatecla.ustumlserver.domain.model.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MethodEntity {

    private String name;
    private String type;
    private List<Modifier> modifiers;

    private List<ParameterEntity> parametersEntities;

    public MethodEntity(Method method) {
        BeanUtils.copyProperties(method, this);
        this.parametersEntities = new ArrayList<>();
        for (Parameter parameter: method.getParameters()) {
            this.parametersEntities.add(new ParameterEntity(parameter));
        }
    }

    public Method toMethod() {
        Method method = new Method();
        BeanUtils.copyProperties(this, method);
        List<Parameter> parameters = new ArrayList<>();
        if (Objects.nonNull(this.parametersEntities)) {
            for (ParameterEntity parameterEntity: this.parametersEntities){
                parameters.add(parameterEntity.toParameter());
            }
        }
        method.setParameters(parameters);
        return method;
    }

}
