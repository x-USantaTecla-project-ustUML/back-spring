package com.usantatecla.ustumlserver.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDto {

    @JsonProperty("class")
    @NotEmpty
    @NotNull
    private String clazz;
    private List<String> modifiers;
    private List<DefinitionDto> definitions;

}
