package be.ifapme.election.controller.rest;

import be.ifapme.election.service.CsvService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/csv")
public class CreateCsvFileController {
    private final CsvService csvService;

    public CreateCsvFileController(CsvService csvService) {
        this.csvService = csvService;
    }

    @GetMapping("/{electionId}")
    public String createFile(@PathVariable Integer electionId) throws IOException, IllegalAccessException {
        return csvService.createFile(electionId);
    }
}
