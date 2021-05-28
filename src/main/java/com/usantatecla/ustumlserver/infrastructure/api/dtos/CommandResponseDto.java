package com.usantatecla.ustumlserver.infrastructure.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandResponseDto {

    private String plantUML;
    private String ustUML;
    private String directoryTree;

}
