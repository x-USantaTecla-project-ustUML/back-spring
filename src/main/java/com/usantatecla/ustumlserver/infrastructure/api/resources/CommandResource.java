package com.usantatecla.ustumlserver.infrastructure.api.resources;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.PlantUMLGenerator;
import com.usantatecla.ustumlserver.domain.model.UstUMLGenerator;
import com.usantatecla.ustumlserver.domain.services.Command;
import com.usantatecla.ustumlserver.domain.services.CommandService;
import com.usantatecla.ustumlserver.infrastructure.api.Rest;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandResponseDto;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;

@Rest
@PreAuthorize("authenticated")
@RequestMapping(CommandResource.COMMAND)
public class CommandResource {

    static final String COMMAND = "/command";

    private CommandService commandService;

    @Autowired
    public CommandResource(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public CommandResponseDto executeCommand(@RequestBody Map<String, Object> jsonObject) {
        Member member = this.commandService.execute(new Command(new JSONObject(jsonObject)),
                RequestContextHolder.currentRequestAttributes().getSessionId());
        return new CommandResponseDto(member.accept(new PlantUMLGenerator()), member.accept(new UstUMLGenerator()));
    }

}
