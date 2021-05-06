package com.usantatecla.ustumlserver.api.resources;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class CommandResponseDto {

    private String plantUML;
    private String ustUML;

}
