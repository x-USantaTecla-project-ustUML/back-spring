package com.usantatecla.ustumlserver.api.resources;

import com.usantatecla.ustumlserver.api.dtos.ClassDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(CommandResource.COMMAND)
public class CommandResource {

    static final String COMMAND = "/command";

    @PostMapping
    public void createUser(@Valid @RequestBody ClassDto classDto) {
        System.out.println(classDto.toString());
    }

}
