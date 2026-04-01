package com.resepti.resepti.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.resepti.resepti.entity.Ainesosa;
import com.resepti.resepti.entity.Kayttaja;
import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.entity.ReseptiAines;
import com.resepti.resepti.entity.Tag;
import com.resepti.resepti.repository.AinesosaRepo;
import com.resepti.resepti.repository.KayttajaRepo;
import com.resepti.resepti.repository.ReseptiAinesRepo;
import com.resepti.resepti.repository.ReseptiRepo;
import com.resepti.resepti.repository.TagRepo;

@Component
public class DataInitializer implements CommandLineRunner {

  private final ReseptiRepo reseptiRepo;
  private final AinesosaRepo ainesosaRepo;
  private final ReseptiAinesRepo reseptiAinesRepo;
  private final TagRepo tagRepo;
  private final KayttajaRepo kayttajaRepo;

  public DataInitializer(ReseptiRepo reseptiRepo, AinesosaRepo ainesosaRepo, ReseptiAinesRepo reseptiAinesRepo,
      TagRepo tagRepo, com.resepti.resepti.repository.KayttajaRepo kayttajaRepo) {
    this.reseptiRepo = reseptiRepo;
    this.ainesosaRepo = ainesosaRepo;
    this.reseptiAinesRepo = reseptiAinesRepo;
    this.tagRepo = tagRepo;
    this.kayttajaRepo = kayttajaRepo;
  }

  @Override
  public void run(String... args) {
    seedAinesosat();
    seedTagit();
    seedReseptit();
    seedReseptiAines();
    seedKayttajat();
  }

  // KAYTTAJAT
  private void seedKayttajat() {
    if (!kayttajaRepo.existsByKayttajatunnus("admin")) {
      kayttajaRepo.save(new Kayttaja("admin", "$2a$12$OcgbOjhsqrX/xYweAl1X.Osh1b4gkEaqTI/Gm1I3wEWuvx5sKszm.", "ADMIN"));
    }

    if (!kayttajaRepo.existsByKayttajatunnus("user")) {
      kayttajaRepo.save(new Kayttaja("user", "$2a$12$zvZQ4K9Fp9NYLwai7RTf..eZPM2zR6xiCHSY6PiuoJugrbEFVOln6", "USER"));
    }
  }

  // AINESOSAT
  private Ainesosa haeTaiLisaaAinesosa(String nimi) {
    return ainesosaRepo.findByNimi(nimi)
        .orElseGet(() -> ainesosaRepo.save(new Ainesosa(nimi)));
  }

  private void seedAinesosat() {
    haeTaiLisaaAinesosa("paprika");
    haeTaiLisaaAinesosa("pasta");
    haeTaiLisaaAinesosa("pekoni");
    haeTaiLisaaAinesosa("vehnäjauho");
    haeTaiLisaaAinesosa("maito");
    haeTaiLisaaAinesosa("suola");
    haeTaiLisaaAinesosa("kananmuna");
    haeTaiLisaaAinesosa("sokeri");
    haeTaiLisaaAinesosa("sipuli");
    haeTaiLisaaAinesosa("valkosipuli");
    haeTaiLisaaAinesosa("basilika");
    haeTaiLisaaAinesosa("pippuri");
    haeTaiLisaaAinesosa("parmesaani");
    haeTaiLisaaAinesosa("vanilliinisokeri");
  }

  // TAGIT
  private Tag haeTaiLisaaTagi(String nimi) {
    return tagRepo.findByNimi(nimi)
        .orElseGet(() -> tagRepo.save(new Tag(nimi)));
  }

  private void seedTagit() {
    haeTaiLisaaTagi("texmex");
    haeTaiLisaaTagi("vegaaninen");
    haeTaiLisaaTagi("alkuruoka");
    haeTaiLisaaTagi("jälkiruoka");
    haeTaiLisaaTagi("arkiruoka");
    haeTaiLisaaTagi("nopea");
    haeTaiLisaaTagi("helppo");
  }

  // RESEPTIT
  private void seedReseptit() {
    if (reseptiRepo.findByNimi("Pasta Carbonara").isEmpty()) {

      Tag arkiruoka = haeTaiLisaaTagi("arkiruoka");
      Tag nopea = haeTaiLisaaTagi("nopea");
      Tag helppo = haeTaiLisaaTagi("helppo");

      Resepti carbonara = new Resepti("Pasta Carbonara", "Herkullinen arjen pelastaja",
          "Laita pastavesi kiehumaan;Pilko sipuli, valkosipuli ja lisää ne kuumaan pannuun;Ruskista jauheliha;Lisää mausteet;Anna kastikkeen hautua niin kauan kunnes pasta on kypsää;Koristele tuoreilla yrteillä ja raasta päälle parmesaania",
          85, 4);
      carbonara = reseptiRepo.save(carbonara);

      if (carbonara.getTags() == null) {
        carbonara.setTags(new java.util.HashSet<>());
      }

      carbonara.getTags().add(arkiruoka);
      carbonara.getTags().add(nopea);
      carbonara.getTags().add(helppo);

      reseptiRepo.save(carbonara);
    }

    if (reseptiRepo.findByNimi("Pannukakku").isEmpty()) {

      Tag jalkiruoka = haeTaiLisaaTagi("jälkiruoka");
      Tag helppo = haeTaiLisaaTagi("helppo");
      Tag nopea = haeTaiLisaaTagi("nopea");

      Resepti pannukakku = new Resepti("Pannukakku", "Maailman herkullisin pannari",
          "Sekoita kuivat ainekset;Lisää maito, kananmunat ja sekoita huolellisesti;Kaada taikina uunipellille;Laita 200 asteiseen uuniin 45 minuutiksi",
          60, 5);

      if (pannukakku.getTags() == null) {
        pannukakku.setTags(new java.util.HashSet<>());
      }

      pannukakku.getTags().add(jalkiruoka);
      pannukakku.getTags().add(helppo);
      pannukakku.getTags().add(nopea);

      reseptiRepo.save(pannukakku);
    }
  }

  // RESEPTIAINES
  private void seedReseptiAines() {

    if (reseptiAinesRepo.count() > 0) {
      return;
    }

    // Hae tai lisää ainesosat
    Ainesosa pasta = haeTaiLisaaAinesosa("pasta");
    Ainesosa pekoni = haeTaiLisaaAinesosa("pekoni");
    Ainesosa parmesaani = haeTaiLisaaAinesosa("parmesaani");

    Ainesosa vehnajauho = haeTaiLisaaAinesosa("vehnäjauho");
    Ainesosa maito = haeTaiLisaaAinesosa("maito");
    Ainesosa sokeri = haeTaiLisaaAinesosa("sokeri");
    Ainesosa kananmuna = haeTaiLisaaAinesosa("kananmuna");
    Ainesosa suola = haeTaiLisaaAinesosa("suola");
    Ainesosa vanilliinisokeri = haeTaiLisaaAinesosa("vanilliinisokeri");

    // Hae reseptit
    Resepti carbonara = reseptiRepo.findByNimi("Pasta Carbonara")
        .orElseThrow(() -> new RuntimeException("Pasta Carbonara puuttuu"));

    Resepti pannukakku = reseptiRepo.findByNimi("Pannukakku")
        .orElseThrow(() -> new RuntimeException("Pannukakku puuttuu"));

    // carbonara
    reseptiAinesRepo.save(new ReseptiAines(carbonara, pasta, 1, "pkt"));
    reseptiAinesRepo.save(new ReseptiAines(carbonara, pekoni, 200, "g"));
    reseptiAinesRepo.save(new ReseptiAines(carbonara, parmesaani, 100, "g"));

    // pannukakku
    reseptiAinesRepo.save(new ReseptiAines(pannukakku, vehnajauho, 4, "dl"));
    reseptiAinesRepo.save(new ReseptiAines(pannukakku, maito, 7, "dl"));
    reseptiAinesRepo.save(new ReseptiAines(pannukakku, sokeri, 1.5, "dl"));
    reseptiAinesRepo.save(new ReseptiAines(pannukakku, vanilliinisokeri, 2, "tl"));
    reseptiAinesRepo.save(new ReseptiAines(pannukakku, suola, 1, "tl"));
    reseptiAinesRepo.save(new ReseptiAines(pannukakku, kananmuna, 3, "kpl"));
  }

}
