-- Supprimer la table si elle existe déjà
DROP TABLE IF EXISTS erreur_json;

-- Créer la table
CREATE TABLE IF NOT EXISTS erreur_json (
    id SERIAL PRIMARY KEY, -- PostgreSQL utilise SERIAL pour l'auto-incrémentation
    nom_fichier VARCHAR(255) DEFAULT NULL,
    message_erreur VARCHAR(255) DEFAULT NULL,
    date TIMESTAMP NOT NULL -- PostgreSQL utilise TIMESTAMP pour les dates et heures
);
