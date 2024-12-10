-- 1. Ajouter la colonne `password` avec une valeur par défaut
ALTER TABLE personne 
ADD COLUMN password VARCHAR(255) NOT NULL DEFAULT '$2a$10$zNv/YHKwvx56B7pCDEfnfO3x/0kPf3vWX3I/NcpgqlZFpzxkqJc1S';

-- 2. Modifier la colonne `password` pour spécifier la collation (similaire à MySQL utf8mb4)
ALTER TABLE personne
ALTER COLUMN password TYPE VARCHAR(255) COLLATE "utf8_general_ci";

-- 3. add colonne role
ALTER TABLE personne ADD COLUMN role SMALLINT NOT NULL DEFAULT 0;