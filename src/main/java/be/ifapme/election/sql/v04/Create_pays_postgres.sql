CREATE TABLE IF NOT EXISTS public.pays
(
    code_pays character varying COLLATE pg_catalog."default" NOT NULL,
    nom character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pays_pkey PRIMARY KEY (code_pays)
    );

ALTER TABLE adresse
ADD COLUMN code_pays VARCHAR(20) NOT NULL;

INSERT INTO pays (code_pays, nom) VALUES ('BE', 'Belgique');

UPDATE adresse
SET code_pays = 'BE'
WHERE code_pays = '';

UPDATE election
SET code_pays = 'BE'
WHERE code_pays = '';

ALTER TABLE IF EXISTS public.adresse
    ADD FOREIGN KEY (code_pays)
    REFERENCES public.pays (code_pays) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;
ALTER TABLE IF EXISTS public.election
    ADD COLUMN code_pays character varying;
ALTER TABLE IF EXISTS public.election
    ADD FOREIGN KEY (code_pays)
    REFERENCES public.pays (code_pays) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;