package be.ifapme.election.controller.rest;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreateElectionCommand;
import be.ifapme.election.dto.ElectionDto;
import be.ifapme.election.service.ElectionService;
import com.itextpdf.text.DocumentException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/election")
public class ElectionController {
    private final ElectionService electionService;

    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @PostMapping
    public ElectionDto createElection(@RequestBody CreateElectionCommand command) throws BusinessException, DocumentException, IOException {
        return electionService.create(command);
    }
}
