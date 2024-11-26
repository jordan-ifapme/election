package be.ifapme.election.controller.rest;

import be.ifapme.election.command.CreatePartiCommand;
import be.ifapme.election.dto.PartiDto;
import be.ifapme.election.service.PartiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/parti")
public class PartiController {
    private final PartiService partiService;
    public PartiController(PartiService partiService) {
        this.partiService = partiService;
    }

    @PostMapping
    public PartiDto createParti(@RequestBody CreatePartiCommand command) {
        return partiService.createParti(command);
    }
}
