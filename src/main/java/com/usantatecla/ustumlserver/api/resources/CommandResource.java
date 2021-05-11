package com.usantatecla.ustumlserver.api.resources;

import com.usantatecla.ustumlserver.api.Rest;
import com.usantatecla.ustumlserver.api.dtos.CommandResponseDto;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.PlantUMLGenerator;
import com.usantatecla.ustumlserver.domain.model.UstUMLGenerator;
import com.usantatecla.ustumlserver.domain.services.Command;
import com.usantatecla.ustumlserver.domain.services.PackageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Rest
@RequestMapping(CommandResource.COMMAND)
public class CommandResource {

    static final String COMMAND = "/command";

    private PackageService packageService;

    @Autowired
    public CommandResource(PackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping
    public CommandResponseDto executeCommand(@RequestBody Map<String, Object> jsonObject) {
        Member member = this.packageService.get(new Command(new JSONObject(jsonObject)));
        return new CommandResponseDto(member.accept(new PlantUMLGenerator()), member.accept(new UstUMLGenerator()));
    }

}
