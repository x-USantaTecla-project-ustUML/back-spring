package com.usantatecla.ustumlserver.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefinitionDto {

    @Pattern(regexp = "[0-9]+")
    private String definition;

}
