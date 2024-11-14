package be.ifapme.election.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PartiDto {
    private Integer id;
    private String nom;
    private String couleur;
}
