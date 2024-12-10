package be.ifapme.election.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "countryClient", url = "https://restcountries.com/v3.1")
public interface CountryFeignClient {

    @GetMapping("/alpha/{code}")
    String getCountryByCode(@PathVariable("code") String code);
}
