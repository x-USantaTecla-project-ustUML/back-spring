package com.usantatecla.ustumlserver.infrastructure.api.resources;

import com.usantatecla.ustumlserver.domain.model.DirectoryTreeGenerator;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.PlantUMLGenerator;
import com.usantatecla.ustumlserver.domain.model.UstUMLGenerator;
import com.usantatecla.ustumlserver.domain.services.CommandService;
import com.usantatecla.ustumlserver.infrastructure.api.Rest;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandResponseDto;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public CommandResponseDto executeCommand(@RequestBody Map<String, Object> jsonObject, HttpSession httpSession,
                                             @RequestHeader("Authorization") String token) {
        Command command = new Command(new JSONObject(jsonObject));
        this.commandService.execute(command, httpSession.getId(), token);
        Member member = this.commandService.getContext(httpSession.getId(), token);
        return this.getCommandResponseDto(member);
    }

    @GetMapping
    public CommandResponseDto getContext(HttpSession httpSession, @RequestHeader("Authorization") String token) {
        Member member = this.commandService.getContext(httpSession.getId(), token);
        return this.getCommandResponseDto(member);
    }

    private CommandResponseDto getCommandResponseDto(Member member) {
        String plantUML = new PlantUMLGenerator().generate(member);
        String ustUml = new UstUMLGenerator().generate(member);
        String directoryTree = new DirectoryTreeGenerator().generate(this.commandService.getAccount());
        return new CommandResponseDto(plantUML, ustUml, directoryTree);
    }

}
