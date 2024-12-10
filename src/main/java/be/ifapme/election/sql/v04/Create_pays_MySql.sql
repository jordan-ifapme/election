CREATE TABLE IF NOT EXISTS pays (
                                    code_pays VARCHAR(255) NOT NULL,
    nom VARCHAR(255) NOT NULL,
    PRIMARY KEY (code_pays)
    );

-- Ajout de la clé étrangère dans la table adresse
ALTER TABLE adresse
    ADD CONSTRAINT fk_code_pays
        FOREIGN KEY (code_pays)
            REFERENCES pays (code_pays)
            ON UPDATE NO ACTION
            ON DELETE NO ACTION;

-- Ajout de la colonne code_pays dans la table election
ALTER TABLE election
    ADD COLUMN code_pays VARCHAR(255);

-- Ajout de la clé étrangère dans la table election
ALTER TABLE election
    ADD CONSTRAINT fk_code_pays_election
        FOREIGN KEY (code_pays)
            REFERENCES pays (code_pays)
            ON UPDATE NO ACTION
            ON DELETE NO ACTION;

