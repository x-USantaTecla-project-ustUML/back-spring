package com.usantatecla.ustumlserver.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandResponseDto {

    private String plantUML;
    private String ustUML;

}
