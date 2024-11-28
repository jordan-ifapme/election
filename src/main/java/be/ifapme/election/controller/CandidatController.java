package be.ifapme.election.controller;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreateCandidatCommand;
import be.ifapme.election.dto.CandidatDto;
import be.ifapme.election.service.CandidatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidat")
public class CandidatController {
    private CandidatService candidatService;

    public CandidatController(CandidatService candidatService) {
        this.candidatService = candidatService;
    }

    @PostMapping
    CandidatDto create(@RequestBody CreateCandidatCommand command) throws BusinessException {
        return candidatService.create(command);
    }
}