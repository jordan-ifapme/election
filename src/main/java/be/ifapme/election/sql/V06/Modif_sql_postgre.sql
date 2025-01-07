-- 1. Modification de colonnes dans `adresse`
ALTER TABLE adresse
ALTER COLUMN localite TYPE VARCHAR(255),
ALTER COLUMN code_postal TYPE VARCHAR(10),
ALTER COLUMN rue TYPE VARCHAR(255),
ALTER COLUMN boite TYPE VARCHAR(255);

-- 2. Suppression et ajout d'une clé étrangère dans `candidat`
ALTER TABLE candidat DROP CONSTRAINT IF EXISTS fk_candidat_partit;

ALTER TABLE candidat
    ADD CONSTRAINT fk_candidat_partit
        FOREIGN KEY (partit_id)
            REFERENCES partit(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

-- 3. Modification de colonnes dans `candidat`
ALTER TABLE candidat
ALTER COLUMN partit_id TYPE BIGINT,
ALTER COLUMN vote TYPE BIGINT,
ALTER COLUMN vote SET DEFAULT 0;

-- 4. Suppression et ajout d'une clé étrangère dans `election`
ALTER TABLE election DROP CONSTRAINT IF EXISTS fk_code_pays_election;

ALTER TABLE election
    ADD CONSTRAINT fk_code_pays_election
        FOREIGN KEY (code_pays)
            REFERENCES pays(code_pays)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

-- 5. Modification de colonnes dans `election`
ALTER TABLE election
ALTER COLUMN nom TYPE VARCHAR(255),
ALTER COLUMN date_limite TYPE TIMESTAMP,
ALTER COLUMN date_limite SET DEFAULT CURRENT_TIMESTAMP,
ALTER COLUMN code_pays TYPE VARCHAR(255);

-- 6. Modification de colonnes dans `partit`
ALTER TABLE partit
ALTER COLUMN nom TYPE VARCHAR(255),
ALTER COLUMN couleur TYPE VARCHAR(50);

-- 7. Suppression et ajout d'une clé étrangère dans `personne`
ALTER TABLE personne DROP CONSTRAINT IF EXISTS fk_personne_adresse;

ALTER TABLE personne
    ADD CONSTRAINT fk_personne_adresse
        FOREIGN KEY (adresse_id)
            REFERENCES adresse(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

-- 8. Modification de colonnes dans `personne`
ALTER TABLE personne
ALTER COLUMN nom TYPE VARCHAR(255),
ALTER COLUMN prenom TYPE VARCHAR(255),
ALTER COLUMN registre_national TYPE VARCHAR(20),
ALTER COLUMN adresse_id TYPE BIGINT;
