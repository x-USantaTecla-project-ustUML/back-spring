package com.usantatecla.ustumlserver.infrastructure.api.resources;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.generators.DirectoryTreeGenerator;
import com.usantatecla.ustumlserver.domain.model.generators.PlantUMLGenerator;
import com.usantatecla.ustumlserver.domain.model.generators.UstUMLGenerator;
import com.usantatecla.ustumlserver.domain.services.AccountService;
import com.usantatecla.ustumlserver.domain.services.CommandService;
import com.usantatecla.ustumlserver.infrastructure.api.Rest;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandResponseDto;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Rest
@PreAuthorize("authenticated")
@RequestMapping(CommandResource.COMMAND)
public class CommandResource {

    static final String COMMAND = "/command";

    private AccountService accountService;
    private CommandService commandService;

    @Autowired
    public CommandResource(AccountService accountService, CommandService commandService) {
        this.accountService = accountService;
        this.commandService = commandService;
    }

    @PostMapping
    public CommandResponseDto executeCommand(@RequestBody Map<String, Object> jsonObject, HttpSession httpSession) {
        Command command = new Command(new JSONObject(jsonObject));
        Member member = this.commandService.execute(command, httpSession.getId());
        return this.getCommandResponseDto(member);
    }

    @GetMapping
    public CommandResponseDto getContext(HttpSession httpSession) {
        Member member = this.commandService.getContext(httpSession.getId());
        return this.getCommandResponseDto(member);
    }

    private CommandResponseDto getCommandResponseDto(Member member) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String activeMemberID = member.getId();
        String plantUML = new PlantUMLGenerator().generate(member);
        String ustUml = new UstUMLGenerator().generate(member);
        String directoryTree = new DirectoryTreeGenerator().generate(this.accountService.read(email));
        return new CommandResponseDto(activeMemberID, plantUML, ustUml, directoryTree);
    }

}
