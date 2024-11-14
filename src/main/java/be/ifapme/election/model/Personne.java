package be.ifapme.election.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "personne")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Personne {
    @Id
    @ColumnDefault("nextval('personne_id_seq'")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "registre_national", length = 20)
    private String registreNational;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adresse_id")
    private Adresse adresse;

}
