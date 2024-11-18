package be.ifapme.election.controller;

import be.ifapme.election.command.CreateAdresseCommand;
import be.ifapme.election.dto.AdresseDto;
import be.ifapme.election.generator.AdresseGenerator;
import be.ifapme.election.service.AdresseService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(value = "api/adresse")
public class AdresseController {

    private final AdresseService adresseService;
    private final AdresseGenerator adresseGenerator;

    public AdresseController(AdresseService adresseService, AdresseGenerator adresseGenerator) {
        this.adresseService = adresseService;
        this.adresseGenerator = adresseGenerator;
    }

    @PostMapping
    public AdresseDto createAdresse(@RequestBody CreateAdresseCommand command) {
        return adresseService.createAdresse(command);
    }

    // pour la generation
    @PostMapping("/generate/{nbrs}")
    public List<AdresseDto> generateRandomAdresses(@PathVariable int nbrs) {
        return adresseService.generateRandomAdresses(nbrs);
    }
}
