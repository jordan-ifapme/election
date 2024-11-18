-- phpMyAdmin SQL Dump

--
-- Structure de la table `adresse`
--

CREATE TABLE `adresse` (
  `id` bigint UNSIGNED NOT NULL,
  `localite` varchar(255) DEFAULT NULL,
  `code_postal` varchar(10) DEFAULT NULL,
  `rue` varchar(255) DEFAULT NULL,
  `boite` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `candidat`
--

CREATE TABLE `candidat` (
  `personne_id` bigint UNSIGNED NOT NULL,
  `election_id` bigint UNSIGNED NOT NULL,
  `partit_id` bigint UNSIGNED DEFAULT NULL,
  `vote` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `election`
--

CREATE TABLE `election` (
  `id` bigint UNSIGNED NOT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `partit`
--

CREATE TABLE `partit` (
  `id` bigint UNSIGNED NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `couleur` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE `personne` (
  `id` bigint UNSIGNED NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `registre_national` varchar(20) DEFAULT NULL,
  `adresse_id` bigint UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `vote`
--

CREATE TABLE `vote` (
  `personne_id` bigint UNSIGNED NOT NULL,
  `election_id` bigint UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `adresse`
--
ALTER TABLE `adresse`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Index pour la table `candidat`
--
ALTER TABLE `candidat`
  ADD PRIMARY KEY (`personne_id`,`election_id`),
  ADD KEY `fk_candidat_election` (`election_id`),
  ADD KEY `fk_candidat_partit` (`partit_id`);

--
-- Index pour la table `election`
--
ALTER TABLE `election`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Index pour la table `partit`
--
ALTER TABLE `partit`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Index pour la table `personne`
--
ALTER TABLE `personne`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD UNIQUE KEY `registre_national` (`registre_national`),
  ADD KEY `fk_personne_adresse` (`adresse_id`);

--
-- Index pour la table `vote`
--
ALTER TABLE `vote`
  ADD PRIMARY KEY (`personne_id`,`election_id`),
  ADD KEY `fk_vote_election` (`election_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `adresse`
--
ALTER TABLE `adresse`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `election`
--
ALTER TABLE `election`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `partit`
--
ALTER TABLE `partit`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `personne`
--
ALTER TABLE `personne`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `candidat`
--
ALTER TABLE `candidat`
  ADD CONSTRAINT `fk_candidat_election` FOREIGN KEY (`election_id`) REFERENCES `election` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_candidat_partit` FOREIGN KEY (`partit_id`) REFERENCES `partit` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_candidat_personne` FOREIGN KEY (`personne_id`) REFERENCES `personne` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `personne`
--
ALTER TABLE `personne`
  ADD CONSTRAINT `fk_personne_adresse` FOREIGN KEY (`adresse_id`) REFERENCES `adresse` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Contraintes pour la table `vote`
--
ALTER TABLE `vote`
  ADD CONSTRAINT `fk_vote_election` FOREIGN KEY (`election_id`) REFERENCES `election` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_vote_personne` FOREIGN KEY (`personne_id`) REFERENCES `personne` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;
