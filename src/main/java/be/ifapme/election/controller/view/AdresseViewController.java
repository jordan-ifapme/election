package be.ifapme.election.controller.view;

import be.ifapme.election.Exception.BusinessException;
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

    @GetMapping("/admin/view/adresse")
    public String adresse(Model model) {
        model.addAttribute("createAdresseCommand", new CreateAdresseCommand());
        return "adresse-formulaire";
    }

    @PostMapping("/admin/view/adresse")
    public String adresse(Model model, CreateAdresseCommand createAdresseCommand) throws BusinessException {
        AdresseDto adresseDto = adresseService.createAdresse(createAdresseCommand);
        model.addAttribute("message", "Adresse crée avec succés");
        model.addAttribute("adresseDto", adresseDto);
        return "adresse-formulaire";
    }

}
