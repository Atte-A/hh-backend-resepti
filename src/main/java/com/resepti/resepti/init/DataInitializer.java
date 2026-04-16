package com.resepti.resepti.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.resepti.resepti.entity.*;
import com.resepti.resepti.repository.*;

@Component
public class DataInitializer implements CommandLineRunner {

  private final ReseptiRepo reseptiRepo;
  private final AinesosaRepo ainesosaRepo;
  private final ReseptiAinesRepo reseptiAinesRepo;
  private final TagRepo tagRepo;
  private final KayttajaRepo kayttajaRepo;

  public DataInitializer(
      ReseptiRepo reseptiRepo,
      AinesosaRepo ainesosaRepo,
      ReseptiAinesRepo reseptiAinesRepo,
      TagRepo tagRepo,
      KayttajaRepo kayttajaRepo) {

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
    seedKayttajat();
    seedReseptit();
    seedReseptiAinekset();
  }

  // Käyttäjät
  private void seedKayttajat() {
    if (!kayttajaRepo.existsByKayttajatunnus("admin")) {
      kayttajaRepo.save(new Kayttaja(
          "admin",
          "$2a$12$OcgbOjhsqrX/xYweAl1X.Osh1b4gkEaqTI/Gm1I3wEWuvx5sKszm.",
          "ADMIN"));
    }

    if (!kayttajaRepo.existsByKayttajatunnus("user")) {
      kayttajaRepo.save(new Kayttaja(
          "user",
          "$2a$12$zvZQ4K9Fp9NYLwai7RTf..eZPM2zR6xiCHSY6PiuoJugrbEFVOln6",
          "USER"));
    }
  }

  // Ainesosat
  private Ainesosa haeTaiLisaaAinesosa(String nimi) {
    // Haetaan ja luodaan puuttuvat
    return ainesosaRepo.findByNimi(nimi)
        .orElseGet(() -> ainesosaRepo.save(new Ainesosa(nimi)));
  }

  private void seedAinesosat() {
    String[] list = {
        "pasta", "pekoni", "parmesaani",
        "kerma", "valkosipuli", "sipuli",
        "suola", "pippuri", "kananmuna",
        "vehnäjauho", "maito", "sokeri",
        "vanilliinisokeri"
    };

    // Käydään loopilla lista läpi ja lisätään puuttuvat
    for (String a : list) {
      haeTaiLisaaAinesosa(a);
    }
  }

  // Tagit
  private Tag haeTaiLisaaTagi(String nimi) {
    return tagRepo.findByNimi(nimi)
        .orElseGet(() -> tagRepo.save(new Tag(nimi)));
  }

  private void seedTagit() {
    String[] tags = {
        "arkiruoka", "nopea", "helppo",
        "jälkiruoka", "italialainen"
    };

    for (String t : tags) {
      haeTaiLisaaTagi(t);
    }
  }

  // Reseptit
  private void seedReseptit() {

    if (reseptiRepo.findByNimi("Pasta Cremosa").isEmpty()) {

      Resepti cremosa = new Resepti(
          "Pasta Cremosa",
          "Kermainen ja helppo pastaruoka",
          "Keitä pasta;Paista pekoni;Lisää kerma;Sekoita juusto;Mausta",
          25,
          4);

      cremosa = reseptiRepo.save(cremosa);

      cremosa.getTags().add(haeTaiLisaaTagi("arkiruoka"));
      cremosa.getTags().add(haeTaiLisaaTagi("nopea"));
      cremosa.getTags().add(haeTaiLisaaTagi("italialainen"));

      reseptiRepo.save(cremosa);
    }

    if (reseptiRepo.findByNimi("Pannukakku").isEmpty()) {

      Resepti pannukakku = new Resepti(
          "Pannukakku",
          "Perinteinen uunipannari",
          "Sekoita taikina;Kaada pellille;Paista uunissa",
          45,
          5);

      pannukakku = reseptiRepo.save(pannukakku);

      pannukakku.getTags().add(haeTaiLisaaTagi("jälkiruoka"));
      pannukakku.getTags().add(haeTaiLisaaTagi("helppo"));

      reseptiRepo.save(pannukakku);
    }
  }

  // ReseptiAinekset
  private void seedReseptiAinekset() {

    // Jos taulussa on jo resepti_ainekset, ei tehdä mitään
    if (reseptiAinesRepo.count() > 0) {
      return;
    }
    
    // Haetaan reseptit
    Resepti cremosa = reseptiRepo.findByNimi("Pasta Cremosa")
        .orElseThrow();

    Resepti pannukakku = reseptiRepo.findByNimi("Pannukakku")
        .orElseThrow();

    // Haetaan ja lisätään puuttuvat ainesosat
    Ainesosa pasta = haeTaiLisaaAinesosa("pasta");
    Ainesosa pekoni = haeTaiLisaaAinesosa("pekoni");
    Ainesosa kerma = haeTaiLisaaAinesosa("kerma");
    Ainesosa parmesaani = haeTaiLisaaAinesosa("parmesaani");

    Ainesosa vehnajauho = haeTaiLisaaAinesosa("vehnäjauho");
    Ainesosa maito = haeTaiLisaaAinesosa("maito");
    Ainesosa sokeri = haeTaiLisaaAinesosa("sokeri");
    Ainesosa kananmuna = haeTaiLisaaAinesosa("kananmuna");

    // Tallennetaan resepti_ainekset reseptiin
    reseptiAinesRepo.save(new ReseptiAines(cremosa, pasta, 1, "pkt"));
    reseptiAinesRepo.save(new ReseptiAines(cremosa, pekoni, 150, "g"));
    reseptiAinesRepo.save(new ReseptiAines(cremosa, kerma, 2, "dl"));
    reseptiAinesRepo.save(new ReseptiAines(cremosa, parmesaani, 80, "g"));

    reseptiAinesRepo.save(new ReseptiAines(pannukakku, vehnajauho, 4, "dl"));
    reseptiAinesRepo.save(new ReseptiAines(pannukakku, maito, 7, "dl"));
    reseptiAinesRepo.save(new ReseptiAines(pannukakku, sokeri, 1.5, "dl"));
    reseptiAinesRepo.save(new ReseptiAines(pannukakku, kananmuna, 3, "kpl"));
  }
}