package be.ifapme.election.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "erreur_json")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErreurJson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('erreur_json_seq'")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nom_fichier")
    private String nomFichier;

    @Column(name = "message_erreur")
    private String messageErreur;

    @Column(name = "date")
    private LocalDateTime date = LocalDateTime.now();

}
