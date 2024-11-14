package be.ifapme.election.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "partit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partit {
    @Id
    @ColumnDefault("nextval('partit_id_seq'")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "couleur", length = 50)
    private String couleur;

}
