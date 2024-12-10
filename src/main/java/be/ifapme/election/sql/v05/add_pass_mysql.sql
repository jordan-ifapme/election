ALTER TABLE `personne` ADD `password` VARCHAR(255) NOT NULL DEFAULT '$2a$10$zNv/YHKwvx56B7pCDEfnfO3x/0kPf3vWX3I/NcpgqlZFpzxkqJc1S' AFTER `registre_national`;
ALTER TABLE `personne` CHANGE `password` `password` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL;
ALTER TABLE `personne` ADD `is_admin` TINYINT NOT NULL DEFAULT '0' AFTER `adresse_id`;