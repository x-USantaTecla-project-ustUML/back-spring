package com.usantatecla.ustumlserver.domain.model.classDiagram;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Parameter {

    private String name;
    private String type;

}
