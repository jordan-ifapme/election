package be.ifapme.election.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "role")
    private Boolean role;

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getRegistreNational() {
        return registreNational;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getRole() {
        return role;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setRegistreNational(String registreNational) {
        this.registreNational = registreNational;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }
}
