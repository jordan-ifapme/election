package be.ifapme.election.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "adresse")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('adresse_id_seq'")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "localite")
    private String localite;

    @Column(name = "code_postal", length = 10)
    private String codePostal;

    @Column(name = "rue")
    private String rue;

    @Column(name = "boite")
    private String boite;

}
