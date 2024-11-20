package be.ifapme.election.service;

import java.io.IOException;

public interface CsvService {
    String createFile(Integer electionId) throws IOException, IllegalAccessException;
}
