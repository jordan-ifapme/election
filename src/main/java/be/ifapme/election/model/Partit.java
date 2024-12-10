package be.ifapme.election.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "partit")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Partit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('adresse_id_seq'")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "couleur", length = 50)
    private String couleur;
}