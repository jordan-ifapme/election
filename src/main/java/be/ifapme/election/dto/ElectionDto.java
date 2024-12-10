package be.ifapme.election.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
public class ElectionDto {
    private Integer id;
    private String nom;
    private LocalDateTime dateLimite;
    private String codePays;
}
