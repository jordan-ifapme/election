package be.ifapme.election.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ElectionStatDto {
    private String electionName;
    private String lastnameCandidat;
    private String firstnameCandidat;
    private String partitName;
    private String partitColor;
    private String localite;
    private String postalCode;
    private String vote;
}
