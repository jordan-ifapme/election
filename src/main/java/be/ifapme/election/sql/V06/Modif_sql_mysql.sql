-- correction table adresse
ALTER TABLE `adresse` CHANGE `localite` `localite` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL, CHANGE `code_postal` `code_postal` VARCHAR(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL, CHANGE `rue` `rue` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL, CHANGE `boite` `boite` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL;
ALTER TABLE election.candidat DROP FOREIGN KEY fk_candidat_partit;
ALTER TABLE election.candidat ADD CONSTRAINT fk_candidat_partit FOREIGN KEY (partit_id) REFERENCES election.partit(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `candidat` CHANGE `partit_id` `partit_id` BIGINT UNSIGNED NOT NULL, CHANGE `vote` `vote` INT NOT NULL DEFAULT '0';
ALTER TABLE election.election DROP FOREIGN KEY fk_code_pays_election;
ALTER TABLE election.election ADD CONSTRAINT fk_code_pays_election FOREIGN KEY (code_pays) REFERENCES election.pays(code_pays) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `election` CHANGE `nom` `nom` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL, CHANGE `date_limite` `date_limite` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, CHANGE `code_pays` `code_pays` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL;
ALTER TABLE `partit` CHANGE `nom` `nom` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL, CHANGE `couleur` `couleur` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL;
ALTER TABLE election.personne DROP FOREIGN KEY fk_personne_adresse;
ALTER TABLE election.personne ADD CONSTRAINT fk_personne_adresse FOREIGN KEY (adresse_id) REFERENCES election.adresse(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `personne` CHANGE `nom` `nom` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL, CHANGE `prenom` `prenom` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL, CHANGE `registre_national` `registre_national` VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL, CHANGE `adresse_id` `adresse_id` BIGINT UNSIGNED NOT NULL;
