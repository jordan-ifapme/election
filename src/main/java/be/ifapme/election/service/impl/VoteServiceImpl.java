package be.ifapme.election.service.impl;

import be.ifapme.election.Exception.*;
import be.ifapme.election.command.CreateVoteCommand;
import be.ifapme.election.dto.VoteDto;
import be.ifapme.election.model.*;
import be.ifapme.election.repository.CandidatRepository;
import be.ifapme.election.repository.ErreurJsonRepository;
import be.ifapme.election.repository.VoteRepository;
import be.ifapme.election.service.ElectionService;
import be.ifapme.election.service.PersonService;
import be.ifapme.election.service.VoteService;
import be.ifapme.election.utils.ModelMapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final PersonService personService;
    private final ElectionService electionService;
    private final CandidatRepository candidatRepository;

    @Value("${chemin.fichier.json.election}")
    private String fichierJson;

    private final ResourceLoader resourceLoader;
    private final ErreurJsonRepository erreurJsonRepository;


    public VoteServiceImpl(VoteRepository voteRepository, PersonService personService, ElectionService electionService, CandidatRepository candidatRepository, ResourceLoader resourceLoader, ErreurJsonRepository erreurJsonRepository) {
        this.voteRepository = voteRepository;
        this.personService = personService;
        this.electionService = electionService;
        this.candidatRepository = candidatRepository;
        this.resourceLoader = resourceLoader;
        this.erreurJsonRepository = erreurJsonRepository;
    }


    @Override
    public VoteDto createvote(CreateVoteCommand command) throws BusinessException {
        Election election = electionService.findById(command.getElectionId());
        // modif pour faire une une verification
        if (election == null) {
            throw new ElectionNotFoundException(command.getElectionId());
        }

        Personne personne = personService.findById(command.getPersonneId());
        // modif pour faire une une verification
        if (personne == null) {
            throw new PersonneNotFoundException(command.getPersonneId());
        }


        VoteId voteId = new VoteId();
        voteId.setPersonneId(command.getPersonneId());
        voteId.setElectionId(command.getElectionId());

        CandidatId candidatId = new CandidatId();
        candidatId.setElectionId(command.getElectionId());
        candidatId.setPersonneId(command.getCandidatId());

        Candidat aEteVote = candidatRepository.findById(candidatId).orElse(null);
        if (aEteVote == null) {
            throw new CandidatNotFoundException(candidatId.getPersonneId());
        }


        Vote aDejaVote = voteRepository.findById(voteId).orElse(null);

        if(aDejaVote != null){
            throw new AlreadyVotedException(personne.getNom(), personne.getPrenom());
        }

        aEteVote.setVote(aEteVote.getVote() + 1);
        candidatRepository.save(aEteVote);

        Vote createvote = Vote.builder()
                .id(voteId)
                .personne(personne)
                .election(election).build();



        Vote savedVote = voteRepository.save(createvote);

        return ModelMapperUtils
                .getInstance()
                .map(savedVote, VoteDto.class);
    }

    @Override
    public ResponseEntity<String> votejson() throws BusinessException {
        /*
        * TODO
        * - faut t'il a chaque fois vider ltable des erreurs a chaque lanncement ?
        * a faire
        * - cree un json des donnée qui sont en erreur
        * a chaque lancement il faut refaire le fichier d'erreur ou pas ?
        */

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

                    this.createvote(command);
                } catch (BusinessException e) {
                    // Sauvegarder l'erreur dans la base
                    ErreurJson erreurJson = new ErreurJson();
                    erreurJson.setNomFichier(fichierJson);
                    erreurJson.setMessageErreur(e.getMessage());
                    erreurJson.setJson("Ligne du fichier qui passe pas");
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
