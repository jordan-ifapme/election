package be.ifapme.election.controller.view;

import be.ifapme.election.command.CreatePartiCommand;
import be.ifapme.election.dto.PartiDto;

import be.ifapme.election.service.PartiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "view/partit")
public class PartitController {

    private final PartiService partiService;
    public PartitController(PartiService partiService) {
        this.partiService = partiService;
    }


    @GetMapping("/formulaire")
    public String afficherFormulaire(Model model) {
        model.addAttribute("CreatePartiCommand", new CreatePartiCommand());
        return "partit";
    }

    @PostMapping("/formulaire")
    public String soumettreFormulaire(CreatePartiCommand createPartiCommand, Model model) {

        // Appeler un service qui save la partit
        PartiDto partiDto = partiService.createParti(createPartiCommand);

        model.addAttribute("CreatePartiCommand", new CreatePartiCommand());

        model.addAttribute("message", "Personne ajoutée avec succès !");
        model.addAttribute("partiDto", partiDto);
        return "partit";
    }
}
