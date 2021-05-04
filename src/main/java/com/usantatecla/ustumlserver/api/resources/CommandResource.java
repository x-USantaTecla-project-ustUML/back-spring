package com.usantatecla.ustumlserver.api.resources;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.PlantUMLGenerator;
import com.usantatecla.ustumlserver.domain.services.Command;
import com.usantatecla.ustumlserver.domain.services.PackageService;
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
    public String executeCommand(@RequestBody Map<String, Object> jsonObject) {
        Member member = new PackageService().get(new Command(new JSONObject(jsonObject)));
        PlantUMLGenerator plantUMLGenerator = new PlantUMLGenerator();
        member.accept(plantUMLGenerator);
        return plantUMLGenerator.toString();
    }

}
