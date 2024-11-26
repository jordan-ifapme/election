package be.ifapme.election.controller.view;

import be.ifapme.election.command.CreateAdresseCommand;
import be.ifapme.election.dto.AdresseDto;
import be.ifapme.election.service.AdresseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdresseViewController {

    private final AdresseService adresseService;

    public AdresseViewController(AdresseService adresseService) {
        this.adresseService = adresseService;
    }

    @GetMapping("adresse")
    public String index(Model model) {
        model.addAttribute("createAdresseCommand", new CreateAdresseCommand());
        return "adresse";
    }

    @PostMapping("/postForm")
    public String postForm(CreateAdresseCommand createAdresseCommand, Model model) {
        AdresseDto adresseDto = adresseService.createAdresse(createAdresseCommand);

        model.addAttribute("message", "Adresse ajoutée avec succès");
        model.addAttribute("adresseDto", adresseDto);

        return "adresse";
    }
}
