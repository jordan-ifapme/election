package be.ifapme.election.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "personne")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name= "password")
    private String password;

    @Column(name = "is_admin")
    private Boolean is_admin;

    @Column(name = "birth_date")
    private LocalDateTime birth_date;

    @Column(name = "gender")
    private String gender;

}
