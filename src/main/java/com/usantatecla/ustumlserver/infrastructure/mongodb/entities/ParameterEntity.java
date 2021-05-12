package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Method;
import com.usantatecla.ustumlserver.domain.model.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class ParameterEntity {
    @Id
    private String id;
    private String name;
    private String type;

    public Parameter toParameter() {
        Parameter parameter = new Parameter();
        BeanUtils.copyProperties(this, parameter);
        return parameter;
    }

}
