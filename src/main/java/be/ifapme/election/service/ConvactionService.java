package be.ifapme.election.service;

import be.ifapme.election.model.Election;
import be.ifapme.election.model.Personne;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;

public interface ConvactionService {
    void createConvocation(Election election) throws DocumentException, FileNotFoundException;
}
