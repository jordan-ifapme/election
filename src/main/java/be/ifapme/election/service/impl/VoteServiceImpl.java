package be.ifapme.election.service.impl;

import be.ifapme.election.Exception.*;
import be.ifapme.election.Exception.ElectionFinishedException;
import be.ifapme.election.command.CreateFullVoteCommand;
import be.ifapme.election.command.CreateVoteCommand;
import be.ifapme.election.dto.VoteDto;
import be.ifapme.election.model.*;
import be.ifapme.election.repository.*;
import be.ifapme.election.service.ElectionService;
import be.ifapme.election.service.PersonService;
import be.ifapme.election.service.VoteService;
import be.ifapme.election.utils.ModelMapperUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;


import java.io.IOException;
import java.io.InputStream;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final PersonService personService;
    private final ElectionService electionService;
    private final CandidatRepository candidatRepository;
    private final ElectionRepository electionRepository;

    @Value("${chemin.fichier.json.election}")
    private String fichierJson;

    private final ResourceLoader resourceLoader;
    private final ErreurJsonRepository erreurJsonRepository;


    public VoteServiceImpl(VoteRepository voteRepository, PersonService personService, ElectionService electionService, CandidatRepository candidatRepository, ResourceLoader resourceLoader, ErreurJsonRepository erreurJsonRepository, ElectionRepository electionRepository, PersonRepository personRepository) {
        this.voteRepository = voteRepository;
        this.personService = personService;
        this.electionService = electionService;
        this.candidatRepository = candidatRepository;
        this.resourceLoader = resourceLoader;
        this.erreurJsonRepository = erreurJsonRepository;
        this.electionRepository = electionRepository;
    }


    @Override
    public VoteDto createvote(CreateVoteCommand command) throws BusinessException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Personne personne = personService.findByUserName(username);
        CreateFullVoteCommand fullComand = CreateFullVoteCommand.builder()
                .candidatElectionId(command.getCandidatElectionId())
                .candidatPersonId(command.getCandidatPersonId())
                .personneId(personne.getId())
                .dateVote(LocalDateTime.now()).build();
        return this.createvote(fullComand);
    }

    @Override
    public VoteDto createvote(CreateFullVoteCommand command) throws BusinessException {
        Election election = electionService.findById(command.getCandidatElectionId());
        // modif pour faire une une verification
        if (election == null) {
            throw new ElectionNotFoundException(command.getCandidatElectionId());
        }
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Personne personneVotante = personService.findById(command.getPersonneId());
        if (personneVotante == null) {
            throw new NullVotantException();
        }
        if (!election.getCodePays().equalsIgnoreCase(personneVotante.getAdresse().getCodePays())) {
            throw new NotSameCodeException();
        }

        VoteId voteId = new VoteId();
        voteId.setPersonneId(personneVotante.getId());
        voteId.setElectionId(command.getCandidatElectionId());

        CandidatId candidatId = CandidatId.builder()
                .electionId(command.getCandidatElectionId())
                .personneId(command.getCandidatPersonId()).build();

        Candidat aEteVote = candidatRepository.findById(candidatId).orElse(null);
        Election electionCourrante = electionRepository.findById(command.getCandidatElectionId()).orElse(null);
        if(command.getDateVote() == null){
            command.setDateVote(LocalDateTime.now());
        }
        if(electionCourrante.getDateLimite().isBefore(command.getDateVote())) {
            throw new ElectionFinishedException(command.getCandidatElectionId(), electionCourrante.getDateLimite());
        }

        if (aEteVote == null) {
            throw new CandidatNotFoundException(candidatId.getPersonneId());
        }

        Vote aDejaVote = voteRepository.findById(voteId).orElse(null);

        if(aDejaVote != null){
            throw new AlreadyVotedException(personneVotante.getNom(), personneVotante.getPrenom());
        }

        aEteVote.setVote(aEteVote.getVote() + 1);
        candidatRepository.save(aEteVote);

        Vote createvote = Vote.builder()
                .id(voteId)
                .personne(personneVotante)
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
                    CreateFullVoteCommand command =
                            CreateFullVoteCommand.builder()
                            .personneId(voteJson.getPersonneId())
                            .candidatPersonId(voteJson.getCandidatId())
                            .candidatElectionId(voteJson.getElectionId()).build();
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
