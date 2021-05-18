package com.usantatecla.ustumlserver.infrastructure.api.resources;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.PlantUMLGenerator;
import com.usantatecla.ustumlserver.domain.model.UstUMLGenerator;
import com.usantatecla.ustumlserver.domain.services.Command;
import com.usantatecla.ustumlserver.domain.services.CommandService;
import com.usantatecla.ustumlserver.domain.services.MemberService;
import com.usantatecla.ustumlserver.infrastructure.api.Rest;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandResponseDto;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.WebSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Stack;

@Rest
@RequestMapping(CommandResource.COMMAND)
public class CommandResource {

    static final String COMMAND = "/command";

    private CommandService commandService;

    @Autowired
    public CommandResource(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public CommandResponseDto executeCommand(HttpServletRequest request, @RequestBody Map<String, Object> jsonObject) {
        HttpSession session = request.getSession(true);
        System.out.println(request.);
        Member member = this.commandService.execute(new Command(new JSONObject(jsonObject)), (Stack<MemberService>) session.getAttribute("stack"));
        System.out.println(session.getAttribute("stack"));
        session.setAttribute("stack", "a");
        return new CommandResponseDto(member.accept(new PlantUMLGenerator()), member.accept(new UstUMLGenerator()));
    }

}
