package be.ifapme.election.controller.view;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreatePersonCommand;
import be.ifapme.election.dto.PersonDto;
import be.ifapme.election.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonViewController {

    private final PersonService personService;

    public PersonViewController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/view/person")
    public String home(Model model) {
        model.addAttribute("personne", new CreatePersonCommand());
        return "person";
    }

    @PostMapping("/view/person")
    public String soumettreFormulaire(CreatePersonCommand personnne, Model model) throws BusinessException {

        PersonDto personDto = personService.createPerson(personnne);

        model.addAttribute("message", "Personne ajoutée avec succès !");
        model.addAttribute("personDTO", personDto);
        model.addAttribute("personne", new CreatePersonCommand());
        return "person";
    }
}