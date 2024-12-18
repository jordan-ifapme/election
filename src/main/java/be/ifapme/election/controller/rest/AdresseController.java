package be.ifapme.election.controller.rest;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreateAdresseCommand;
import be.ifapme.election.dto.AdresseDto;
import be.ifapme.election.service.AdresseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/adresse")
public class AdresseController {

    private final AdresseService adresseService;

    public AdresseController(AdresseService adresseService) {
        this.adresseService = adresseService;
    }

    @PostMapping
    public AdresseDto createAdresse(@RequestBody CreateAdresseCommand command) throws BusinessException {
        return adresseService.createAdresse(command);
    }

    @PostMapping("/generate/{nbrs}")
    public List<AdresseDto> generateRandomAdresses(@PathVariable int nbrs) throws BusinessException {
        return adresseService.generateRandomAdresses(nbrs);
    }
}
