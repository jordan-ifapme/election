package be.ifapme.election.service.impl;

import be.ifapme.election.Exception.NotFoundCodeCountryException;
import be.ifapme.election.model.Pays;
import be.ifapme.election.repository.CountryRepository;
import be.ifapme.election.service.CountryFeignClient;
import be.ifapme.election.service.CountryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final CountryFeignClient countryClientService;
    private final ObjectMapper objectMapper;

    public CountryServiceImpl(CountryRepository countryRepository, CountryFeignClient countryClientService, ObjectMapper objectMapper) {
        this.countryRepository = countryRepository;
        this.countryClientService = countryClientService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Pays createPays(String code) throws NotFoundCodeCountryException {
        Pays pays = countryRepository.findPaysByCodePaysIgnoreCase(code).orElse(null);
        if (pays == null) {
            try {
                String json = countryClientService.getCountryByCode(code);
                pays = convertCounrtyApiClientToCountry(json);
                countryRepository.save(pays);
            } catch (Exception e) {
                throw new NotFoundCodeCountryException();
            }
        }
        return pays;
    }

    private Pays convertCounrtyApiClientToCountry(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json).get(0);

        String codeCountry = jsonNode.get("cca2").asText();
        String name = jsonNode.get("name").get("common").asText();

        JsonNode nationalIcon = jsonNode.get("coatOfArms");

        if (nationalIcon.isEmpty()) {
            nationalIcon = jsonNode.get("flags").get("png");
        } else {
            nationalIcon = nationalIcon.get("png");
        }

        return Pays.builder().codePays(codeCountry).nom(name).urlImage(nationalIcon.asText()).build();
    }

}