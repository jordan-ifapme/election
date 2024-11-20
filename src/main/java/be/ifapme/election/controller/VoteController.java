package be.ifapme.election.controller;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreateVoteCommand;
import be.ifapme.election.dto.VoteDto;
import be.ifapme.election.model.ErreurJson;
import be.ifapme.election.model.VoteJson;
import be.ifapme.election.repository.ErreurJsonRepository;
import be.ifapme.election.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;



@Slf4j
@RestController
@RequestMapping("api/vote")
public class VoteController {
    @Value("${chemin.fichier.json.election}")
    private String fichierJson;

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private final ErreurJsonRepository erreurJsonRepository;

    private final VoteService voteService;

    public VoteController(ResourceLoader resourceLoader, ObjectMapper objectMapper, ErreurJsonRepository erreurJsonRepository, VoteService voteService) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
        this.erreurJsonRepository = erreurJsonRepository;
        this.voteService = voteService;
    }

    @PostMapping
    public VoteDto vote(@RequestBody CreateVoteCommand command) throws BusinessException {
        return voteService.createvote(command);
    }


    @GetMapping("/votesjson")
    public ResponseEntity<String> votesjson() throws IOException {

        Resource resource = resourceLoader.getResource(fichierJson);
        ObjectMapper objectMapper = new ObjectMapper();

        String erreurJsonSql = "Requête du fichier : " + fichierJson + " à réussie.";
        HttpStatus httpStatusSql = HttpStatus.OK;

        // Utilisation du try-with-resources pour fermer automatiquement le flux
        try (InputStream inputStream = resource.getInputStream()) {
            // Lire les données JSON dans un tableau de VoteJson
            VoteJson[] votesArray = objectMapper.readValue(inputStream, VoteJson[].class);

            // Traiter les données JSON et les convertir en entités Vote
            for (VoteJson voteJson : votesArray) {
                try {
                    CreateVoteCommand command = new CreateVoteCommand();
                    command.setPersonneId(voteJson.getPersonneId());
                    command.setElectionId(voteJson.getElectionId());
                    command.setCandidatId(voteJson.getCandidatId());

                    voteService.createvote(command);
                } catch (BusinessException e) {
                    // Sauvegarder l'erreur dans la base
                    ErreurJson erreurJson = new ErreurJson();
                    erreurJson.setNomFichier(fichierJson);
                    erreurJson.setMessageErreur(e.getMessage());
                    erreurJsonRepository.save(erreurJson);

                    // Modifier le message et le statut HTTP en cas d'erreur
                    erreurJsonSql = "Une ou plusieurs erreurs lors de l'écriture en DB.";
                    httpStatusSql = HttpStatus.BAD_REQUEST;
                }
            }
        } catch (IOException e) {
            // Gestion des exceptions si le fichier JSON n'est pas trouvé ou si l'inputStream échoue
            e.printStackTrace();
            erreurJsonSql = "Erreur lors de la lecture du fichier : " + fichierJson;
            httpStatusSql = HttpStatus.NOT_FOUND;
        }

        // Retourner la réponse avec le message et le statut appropriés
        return new ResponseEntity<>(erreurJsonSql, httpStatusSql);
    }

}
