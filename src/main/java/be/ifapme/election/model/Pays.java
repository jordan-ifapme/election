package be.ifapme.election.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pays")
public class Pays {
    @Id
    @Column(name = "code_pays", nullable = false, length = Integer.MAX_VALUE)
    private String codePays;

    @Column(name = "nom", nullable = false, length = Integer.MAX_VALUE)
    private String nom;
}