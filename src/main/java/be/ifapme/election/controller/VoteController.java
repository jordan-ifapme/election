package be.ifapme.election.controller;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreateVoteCommand;
import be.ifapme.election.dto.VoteDto;
import be.ifapme.election.repository.ErreurJsonRepository;
import be.ifapme.election.service.VoteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.ResourceLoader;

import java.io.IOException;



@RestController
@RequestMapping("api/vote")
public class VoteController {
    @Value("${chemin.fichier.json.election}")
    private String fichierJson;

    private final ResourceLoader resourceLoader;
    private final ErreurJsonRepository erreurJsonRepository;

    private final VoteService voteService;

    public VoteController(ResourceLoader resourceLoader, ErreurJsonRepository erreurJsonRepository, VoteService voteService) {
        this.resourceLoader = resourceLoader;
        this.erreurJsonRepository = erreurJsonRepository;
        this.voteService = voteService;
    }

    @PostMapping
    public VoteDto vote(@RequestBody CreateVoteCommand command) throws BusinessException {
        return voteService.createvote(command);
    }


    @GetMapping("/votesjson")
    public ResponseEntity<String> votesjson() throws IOException, BusinessException {

        return voteService.votejson();
    }

}
