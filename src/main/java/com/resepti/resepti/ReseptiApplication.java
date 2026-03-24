package com.resepti.resepti;

import com.resepti.resepti.repository.KayttajaRepo;
import com.resepti.resepti.repository.ReseptiAinesRepo;
import com.resepti.resepti.repository.TagRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.resepti.resepti.entity.Ainesosa;
import com.resepti.resepti.entity.Kayttaja;
import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.entity.ReseptiAines;
import com.resepti.resepti.entity.Tag;
import com.resepti.resepti.repository.AinesosaRepo;
import com.resepti.resepti.repository.ReseptiRepo;

@SpringBootApplication
public class ReseptiApplication {

  private final TagRepo tagRepo;
  private final ReseptiAinesRepo reseptiAinesRepo;

  ReseptiApplication(ReseptiAinesRepo reseptiAinesRepo, TagRepo tagRepo) {
    this.reseptiAinesRepo = reseptiAinesRepo;
    this.tagRepo = tagRepo;
  }

  public static void main(String[] args) {
		SpringApplication.run(ReseptiApplication.class, args);
	}

  @Bean
  CommandLineRunner demo(ReseptiRepo reseptiRepo, AinesosaRepo ainesosaRepo, KayttajaRepo kayttajaRepo) {
    return (args) -> {

      // Tagit
      tagRepo.save(new Tag("texmex"));
      tagRepo.save(new Tag("vegaaninen"));
      tagRepo.save(new Tag("alkupala"));
      Tag arkiruoka = tagRepo.save(new Tag("arkiruoka"));
      Tag jalkiruoka = tagRepo.save(new Tag("jälkiruoka"));
      Tag nopea = tagRepo.save(new Tag("nopea"));

      // Reseptit
      Resepti pastaCarbonara = reseptiRepo.save(new Resepti("Pasta Carbonara", "Herkullinen arjen pelastaja", "Laita pastavesi kiehumaan;Pilko sipuli, valkosipuli ja lisää ne kuumaan pannuun;Ruskista jauheliha;Lisää mausteet;Anna kastikkeen hautua niin kauan kunnes pasta on kypsää;Koristele tuoreilla yrteillä ja raasta päälle parmesaania", 85, 4));
      Resepti pannukakku = reseptiRepo.save(new Resepti("Pannukakku", "Maailman herkullisin pannari", "Sekoita kuivat ainekset;Lisää maito, kananmunat ja sekoita huolellisesti;Kaada taikina uunipellille;Laita 200 asteiseen uuniin 45 minuutiksi", 60,5));

      // Ainesosat
      Ainesosa paprika = ainesosaRepo.save(new Ainesosa("paprika"));
      Ainesosa pekoni = ainesosaRepo.save(new Ainesosa("pekoni"));
      Ainesosa pasta = ainesosaRepo.save(new Ainesosa("pasta"));
      Ainesosa vehnajauho = ainesosaRepo.save(new Ainesosa("vehnäjauho"));
      Ainesosa maito = ainesosaRepo.save(new Ainesosa("maito"));
      Ainesosa suola = ainesosaRepo.save(new Ainesosa("suola"));
      Ainesosa kananmuna = ainesosaRepo.save(new Ainesosa("kananmuna"));
      Ainesosa sokeri = ainesosaRepo.save(new Ainesosa("sokeri"));
      ainesosaRepo.save(new Ainesosa("tomaattimurska"));
      ainesosaRepo.save(new Ainesosa("sipuli"));
      ainesosaRepo.save(new Ainesosa("valkosipuli"));
      ainesosaRepo.save(new Ainesosa("basilika"));
      ainesosaRepo.save(new Ainesosa("pippuri"));
      ainesosaRepo.save(new Ainesosa("parmesaani"));


      // Resepti-ainesosa (määrä ja yksikkö)
      reseptiAinesRepo.save(new ReseptiAines(pastaCarbonara, pasta, 1, "pkt"));
      reseptiAinesRepo.save(new ReseptiAines(pastaCarbonara, pekoni, 200, "g"));
      reseptiAinesRepo.save(new ReseptiAines(pastaCarbonara, paprika, 1, "kpl"));
      reseptiAinesRepo.save(new ReseptiAines(pannukakku, vehnajauho, 4, "dl"));
      reseptiAinesRepo.save(new ReseptiAines(pannukakku, maito, 8, "dl"));
      reseptiAinesRepo.save(new ReseptiAines(pannukakku, suola, 1, "tl"));
      reseptiAinesRepo.save(new ReseptiAines(pannukakku, kananmuna, 2, "kpl"));
      reseptiAinesRepo.save(new ReseptiAines(pannukakku, sokeri, 1, "dl"));

      // Lisää tagit ja tallenna reseptiin
      pannukakku.addTag(nopea);
      pannukakku.addTag(jalkiruoka);
      pastaCarbonara.addTag(arkiruoka);
      pastaCarbonara.addTag(nopea);

      reseptiRepo.save(pannukakku);
      reseptiRepo.save(pastaCarbonara);

      // Kayttajat
      kayttajaRepo.save(new Kayttaja("admin", "$2a$12$OcgbOjhsqrX/xYweAl1X.Osh1b4gkEaqTI/Gm1I3wEWuvx5sKszm.", "ADMIN"));
      kayttajaRepo.save(new Kayttaja("user", "$2a$12$zvZQ4K9Fp9NYLwai7RTf..eZPM2zR6xiCHSY6PiuoJugrbEFVOln6","USER"));

    };
  }

}
