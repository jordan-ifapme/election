package be.ifapme.election.service;

import be.ifapme.election.Exception.NotFoundCodeCountryException;
import be.ifapme.election.model.Pays;

public interface CountryService {
    Pays createPays(String code) throws NotFoundCodeCountryException;
}
