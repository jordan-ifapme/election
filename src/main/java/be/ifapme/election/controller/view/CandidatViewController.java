package be.ifapme.election.controller.view;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreateCandidatCommand;
import be.ifapme.election.dto.CandidatDto;
import be.ifapme.election.model.Election;
import be.ifapme.election.model.Partit;
import be.ifapme.election.model.Personne;
import be.ifapme.election.repository.ElectionRepository;
import be.ifapme.election.repository.PartiRepository;
import be.ifapme.election.repository.PersonRepository;
import be.ifapme.election.service.CandidatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CandidatViewController {
    private final CandidatService candidatService;
    private final PersonRepository personRepository;
    private final ElectionRepository electionRepository;
    private final PartiRepository partiRepository;

    public CandidatViewController(
            CandidatService candidatService,
            PersonRepository personRepository,
            ElectionRepository electionRepository,
            PartiRepository partiRepository
    ) {
        this.candidatService = candidatService;
        this.personRepository = personRepository;
        this.electionRepository = electionRepository;
        this.partiRepository = partiRepository;
    }

    @GetMapping("view/candidat")
    public String afficherFormulaire(Model model) {
        selectList(model);
        return "candidat";
    }

    @PostMapping("/view/candidat")
    public String createCandidat(CreateCandidatCommand candidat, Model model) throws BusinessException {

        CandidatDto candidatDto = candidatService.create(candidat);
        selectList(model);
        model.addAttribute("message", "Candidat ajoutée avec succès !");
        model.addAttribute("candidatDto", candidatDto);
        return "candidat";
    }

    private void selectList(Model model) {
        List<Personne> personnes = personRepository.findAll();
        List<Election> elections = electionRepository.findAll();
        List<Partit> partits = partiRepository.findAll();
        model.addAttribute("candidat", new CreateCandidatCommand());
        model.addAttribute("personnes", personnes);
        model.addAttribute("elections", elections);
        model.addAttribute("partits", partits);
    }
}
