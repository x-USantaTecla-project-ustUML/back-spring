package com.usantatecla.ustumlserver.api.resources;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.PlantUMLGenerator;
import com.usantatecla.ustumlserver.domain.services.CommandService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(CommandResource.COMMAND)
public class CommandResource {

    static final String COMMAND = "/command";

    @PostMapping
    public String createUser(@RequestBody Map<String, Object> json) {
        Member member = new CommandService().get(new JSONObject(json));
        PlantUMLGenerator plantUMLGenerator = new PlantUMLGenerator();
        member.accept(plantUMLGenerator);
        return plantUMLGenerator.toString();
    }

}
