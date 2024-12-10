package be.ifapme.election.controller.rest;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.Exception.NotFoundCodeCountryException;
import be.ifapme.election.command.CreateElectionCommand;
import be.ifapme.election.dto.ElectionDto;
import be.ifapme.election.service.ElectionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/election")
public class ElectionController {
    private final ElectionService electionService;

    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @PostMapping
   public ElectionDto createElection(@RequestBody CreateElectionCommand command) throws BusinessException {
        return electionService.create(command);
    }
}
