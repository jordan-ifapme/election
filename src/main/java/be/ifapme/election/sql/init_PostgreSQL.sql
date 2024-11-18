-- Structure de la table `adresse`
CREATE TABLE adresse (
  id BIGSERIAL PRIMARY KEY,
  localite VARCHAR(255) DEFAULT NULL,
  code_postal VARCHAR(10) DEFAULT NULL,
  rue VARCHAR(255) DEFAULT NULL,
  boite VARCHAR(255) DEFAULT NULL
);

-- Structure de la table `candidat`
CREATE TABLE candidat (
  personne_id BIGINT NOT NULL,
  election_id BIGINT NOT NULL,
  partit_id BIGINT DEFAULT NULL,
  vote INTEGER DEFAULT 0,
  PRIMARY KEY (personne_id, election_id),
  FOREIGN KEY (personne_id) REFERENCES personne (id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (election_id) REFERENCES election (id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (partit_id) REFERENCES partit (id) ON DELETE SET NULL ON UPDATE CASCADE
);

-- Structure de la table `election`
CREATE TABLE election (
  id BIGSERIAL PRIMARY KEY,
  nom VARCHAR(255) DEFAULT NULL
);

-- Structure de la table `partit`
CREATE TABLE partit (
  id BIGSERIAL PRIMARY KEY,
  nom VARCHAR(255) DEFAULT NULL,
  couleur VARCHAR(50) DEFAULT NULL
);

-- Structure de la table `personne`
CREATE TABLE personne (
  id BIGSERIAL PRIMARY KEY,
  nom VARCHAR(255) DEFAULT NULL,
  prenom VARCHAR(255) DEFAULT NULL,
  registre_national VARCHAR(20) UNIQUE,
  adresse_id BIGINT DEFAULT NULL,
  FOREIGN KEY (adresse_id) REFERENCES adresse (id) ON DELETE SET NULL ON UPDATE CASCADE
);

-- Structure de la table `vote`
CREATE TABLE vote (
  personne_id BIGINT NOT NULL,
  election_id BIGINT NOT NULL,
  PRIMARY KEY (personne_id, election_id),
  FOREIGN KEY (personne_id) REFERENCES personne (id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (election_id) REFERENCES election (id) ON DELETE CASCADE ON UPDATE CASCADE
);
