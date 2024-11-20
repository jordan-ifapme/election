-- Structure de la table `erreur_json`
--

DROP TABLE IF EXISTS `erreur_json`;
CREATE TABLE IF NOT EXISTS `erreur_json` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom_fichier` varchar(255) DEFAULT NULL,
  `message_erreur` varchar(255) DEFAULT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
COMMIT;