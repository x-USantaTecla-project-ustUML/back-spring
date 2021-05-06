package com.usantatecla.ustumlserver.api.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.PlantUMLGenerator;
import com.usantatecla.ustumlserver.domain.model.UstUMLGenerator;
import com.usantatecla.ustumlserver.domain.services.Command;
import com.usantatecla.ustumlserver.domain.services.PackageService;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @PostMapping
    public Map<String, Object> executeCommand(@RequestBody Map<String, Object> jsonObject) {
        Member member = new PackageService().get(new Command(new JSONObject(jsonObject)));
        JSONObject json = new JSONObject();
        PlantUMLGenerator plantUMLGenerator = new PlantUMLGenerator();
        member.accept(plantUMLGenerator);
        UstUMLGenerator ustUMLGenerator = new UstUMLGenerator();
        member.accept(ustUMLGenerator);
        json.put("plantUML", plantUMLGenerator.toString());
        json.put("ustUML", ustUMLGenerator.toString());

        return new ObjectMapper().readValue(json.toString(), new TypeReference<>() {});
    }

}
