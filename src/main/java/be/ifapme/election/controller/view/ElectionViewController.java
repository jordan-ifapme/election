package be.ifapme.election.controller.view;

import be.ifapme.election.command.CreateElectionCommand;
import be.ifapme.election.dto.ElectionDto;
import be.ifapme.election.service.ElectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ElectionViewController {

    private final ElectionService electionService;

    public ElectionViewController(ElectionService electionService) {
        this.electionService = electionService;
    }


    @GetMapping("/view/election")
    public String afficherFormulaire(Model model) {
        model.addAttribute("createElectionCommand", new CreateElectionCommand());
        return "electionn";
    }

    @PostMapping("/view/election")
    public String soumettreFormulaire(CreateElectionCommand createElectionCommand, Model model) {
        ElectionDto electiondto = electionService.create(createElectionCommand);
        model.addAttribute("message", "Election ajoutée avec succès !");
        model.addAttribute("election", electiondto);


        return "electionn";
    }
}
