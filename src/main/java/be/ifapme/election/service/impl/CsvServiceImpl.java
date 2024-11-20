package be.ifapme.election.service.impl;

import be.ifapme.election.dto.ElectionStatDto;
import be.ifapme.election.model.Candidat;
import be.ifapme.election.repository.CandidatRepository;
import be.ifapme.election.service.CsvService;
import be.ifapme.election.utils.CreateFile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CsvServiceImpl implements CsvService {
    private final CandidatRepository candidatRepository;

    public CsvServiceImpl(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public String createFile(Integer electionId) throws IOException, IllegalAccessException {

        List<Candidat> candidats = candidatRepository.findAllByElection_Id(electionId);
        List<ElectionStatDto> electionStat = candidats.stream().map(candidat -> ElectionStatDto.builder()
                .electionName(candidat.getElection().getNom())
                .lastnameCandidat(candidat.getPersonne().getNom())
                .firstnameCandidat(candidat.getPersonne().getPrenom())
                .partitName(candidat.getPartit().getNom())
                .partitColor(candidat.getPartit().getCouleur())
                .localite(candidat.getPersonne().getAdresse().getLocalite())
                .postalCode(candidat.getPersonne().getAdresse().getCodePostal())
                .vote(String.valueOf(candidat.getVote())).build()).toList();
        CreateFile.createXlsx(electionStat);
        CreateFile.createCSV(electionStat);
        return "La génération s'est bien passé !";
    }
}
