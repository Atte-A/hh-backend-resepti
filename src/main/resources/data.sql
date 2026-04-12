-- kayttajat
INSERT INTO kayttaja (kayttajatunnus, salasana_hashed, rooli)
VALUES ('admin', 'admin', 'ADMIN');

INSERT INTO kayttaja (kayttajatunnus, salasana_hashed, rooli)
VALUES ('user', 'user', 'USER');

-- tagit
INSERT INTO tag (nimi) VALUES ('kasvis');
INSERT INTO tag (nimi) VALUES ('helppo');

-- ainesosat
INSERT INTO ainesosa (nimi) VALUES ('peruna');
INSERT INTO ainesosa (nimi) VALUES ('sipuli');
INSERT INTO ainesosa (nimi) VALUES ('porkkana');

-- resepti
INSERT INTO resepti (nimi, kuvaus, ohje, valmistusaika, annosmaara)
VALUES (
    'Perunakeitto',
    'Helppo ja lämmin keitto',
    'Kuori ja keitä perunat;Lisää sipuli ja porkkana;Soseuta ja mausta.',
    45,
    4
);

-- resepti_tag
INSERT INTO resepti_tag (resepti_id, tag_id)
VALUES (1, 1);

INSERT INTO resepti_tag (resepti_id, tag_id)
VALUES (1, 2);

-- resepti_aines
INSERT INTO resepti_aines (resepti_id, ainesosa_id, maara, yksikko)
VALUES (1, 1, 500, 'g');   -- peruna

INSERT INTO resepti_aines (resepti_id, ainesosa_id, maara, yksikko)
VALUES (1, 2, 1, 'kpl');   -- sipuli

INSERT INTO resepti_aines (resepti_id, ainesosa_id, maara, yksikko)
VALUES (1, 3, 2, 'kpl');   -- porkkana