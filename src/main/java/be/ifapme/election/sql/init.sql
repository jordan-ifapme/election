CREATE TABLE adresse (
                         id SERIAL PRIMARY KEY,
                         localite VARCHAR(255),
                         code_postal VARCHAR(10),
                         rue VARCHAR(255),
                         boite VARCHAR(255)
);

CREATE TABLE personne (
                          id SERIAL PRIMARY KEY,
                          nom VARCHAR(255),
                          prenom VARCHAR(255),
                          registre_national VARCHAR(20) UNIQUE,
                          adresse_id INT REFERENCES adresse(id),
                          est_electeur BOOLEAN DEFAULT FALSE
);

CREATE TABLE partit (
                        id SERIAL PRIMARY KEY,
                        nom VARCHAR(255),
                        couleur VARCHAR(50)
);

CREATE TABLE election (
                          id SERIAL PRIMARY KEY,
                          nom VARCHAR(255)
);

CREATE TABLE candidat (
                          personne_id INT REFERENCES personne(id),
                          election_id INT REFERENCES election(id),
                          partit_id INT REFERENCES partit(id),
                          vote INT DEFAULT 0,
                          PRIMARY KEY (personne_id, election_id)
);

CREATE TABLE vote (
                      personne_id INT REFERENCES personne(id),
                      election_id INT REFERENCES election(id),
                      PRIMARY KEY (personne_id, election_id)
);
