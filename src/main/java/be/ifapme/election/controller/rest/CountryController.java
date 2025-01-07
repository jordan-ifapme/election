package be.ifapme.election.controller.rest;

import be.ifapme.election.Exception.NotFoundCodeCountryException;
import be.ifapme.election.model.Pays;
import be.ifapme.election.service.CountryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/country")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/{code}")
    public Pays createCountry(@PathVariable String code) throws NotFoundCodeCountryException {
        return countryService.createPays(code);
    }
}
