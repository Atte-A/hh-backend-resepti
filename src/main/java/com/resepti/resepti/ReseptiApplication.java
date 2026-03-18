package com.resepti.resepti;

import com.resepti.resepti.repository.KayttajaRepo;
import com.resepti.resepti.repository.ReseptiAinesRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.resepti.resepti.entity.Ainesosa;
import com.resepti.resepti.entity.Kategoria;
import com.resepti.resepti.entity.Kayttaja;
import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.entity.ReseptiAines;
import com.resepti.resepti.repository.AinesosaRepo;
import com.resepti.resepti.repository.KategoriaRepo;
import com.resepti.resepti.repository.ReseptiRepo;

@SpringBootApplication
public class ReseptiApplication {

	private final KayttajaRepo kayttajaRepo;
  private final ReseptiAinesRepo reseptiAinesRepo;

  ReseptiApplication(ReseptiAinesRepo reseptiAinesRepo, KayttajaRepo kayttajaRepo) {
    this.reseptiAinesRepo = reseptiAinesRepo;
    this.kayttajaRepo = kayttajaRepo;
  }

  public static void main(String[] args) {
		SpringApplication.run(ReseptiApplication.class, args);
	}

  @Bean
  CommandLineRunner demo(ReseptiRepo reseptiRepo, AinesosaRepo ainesosaRepo, KategoriaRepo kategoriaRepo, KayttajaRepo kayttajaRepo) {
    return (args) -> {
      // Reseptit
      Resepti pastaCarbonara = reseptiRepo.save(new Resepti("Pasta Carbonara", "Herkullinen arjen pelastaja", "Laita pastavesi kiehumaan;Pilko sipuli, valkosipuli ja lisää ne kuumaan pannuun;Ruskista jauheliha;Lisää mausteet;Anna kastikkeen hautua niin kauan kunnes pasta on kypsää;Koristele tuoreilla yrteillä ja raasta päälle parmesaania", 85, 4));
      reseptiRepo.save(new Resepti("Pannukakku", "Maailman herkullisin pannari", "Sekoita kuivat ainekset;Lisää maito, kananmunat ja sekoita huolellisesti;Kaada taikina uunipellille;Laita 200 asteiseen uuniin 45 minuutiksi", 60,5));

      // Ainesosat
      Ainesosa paprika = ainesosaRepo.save(new Ainesosa("paprika"));
      Ainesosa pekoni = ainesosaRepo.save(new Ainesosa("pekoni"));
      Ainesosa pasta = ainesosaRepo.save(new Ainesosa("pasta"));
      ainesosaRepo.save(new Ainesosa("tomaattimurska"));
      ainesosaRepo.save(new Ainesosa("sipuli"));
      ainesosaRepo.save(new Ainesosa("valkosipuli"));
      ainesosaRepo.save(new Ainesosa("basilika"));
      ainesosaRepo.save(new Ainesosa("kananmuna"));
      ainesosaRepo.save(new Ainesosa("pippuri"));
      ainesosaRepo.save(new Ainesosa("suola"));
      ainesosaRepo.save(new Ainesosa("parmesaani"));

      // Resepti-ainesosa (määrä ja yksikkö)
      reseptiAinesRepo.save(new ReseptiAines(pastaCarbonara, pasta, 1, "pkt"));
      reseptiAinesRepo.save(new ReseptiAines(pastaCarbonara, pekoni, 200, "g"));
      reseptiAinesRepo.save(new ReseptiAines(pastaCarbonara, paprika, 1, "kpl"));

      // Kategoriat
      kategoriaRepo.save(new Kategoria("Alkuruoka"));
      kategoriaRepo.save(new Kategoria("Pääruoka"));
      kategoriaRepo.save(new Kategoria("Jälkiruoka"));
      kategoriaRepo.save(new Kategoria("Välipala"));
      kategoriaRepo.save(new Kategoria("Aamupala"));
      kategoriaRepo.save(new Kategoria("Iltapala"));
      kategoriaRepo.save(new Kategoria("Juoma"));
      kategoriaRepo.save(new Kategoria("Smoothie"));

      // Kayttajat
      kayttajaRepo.save(new Kayttaja("admin", "$2a$12$OcgbOjhsqrX/xYweAl1X.Osh1b4gkEaqTI/Gm1I3wEWuvx5sKszm.", "ADMIN"));
      kayttajaRepo.save(new Kayttaja("user", "$2a$12$zvZQ4K9Fp9NYLwai7RTf..eZPM2zR6xiCHSY6PiuoJugrbEFVOln6","USER"));
    };
  }

}
