package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ParameterEntity {

    private String name;
    private String type;

    public ParameterEntity(Parameter parameter) {
        BeanUtils.copyProperties(parameter, this);
    }

    public Parameter toParameter() {
        Parameter parameter = new Parameter();
        BeanUtils.copyProperties(this, parameter);
        return parameter;
    }

}
