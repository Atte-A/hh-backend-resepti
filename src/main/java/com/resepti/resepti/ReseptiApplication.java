package com.resepti.resepti;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.resepti.resepti.entity.Ainesosa;
import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.repository.AinesosaRepo;
import com.resepti.resepti.repository.ReseptiRepo;

@SpringBootApplication
public class ReseptiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReseptiApplication.class, args);
	}

  @Bean
  CommandLineRunner demo(ReseptiRepo reseptiRepo, AinesosaRepo ainesosaRepo) {
    return (args) -> {
      // Reseptit
      reseptiRepo.save(new Resepti("Pasta Carbonara", "Herkullinen arjen pelasta", "1. Keitä pasta, 2. Tee kastike", 85, 4));
      reseptiRepo.save(new Resepti("Pannukakku", "Maailman herkullisin pannari", "1. Sekoita kuiva aineet, 2. Lisää maito ja kananmunat", 45,5));

      // Ainesosat
      ainesosaRepo.save(new Ainesosa("paprika"));
      ainesosaRepo.save(new Ainesosa("pekoni"));
      ainesosaRepo.save(new Ainesosa("pasta"));
      ainesosaRepo.save(new Ainesosa("tomaattimurska"));
      ainesosaRepo.save(new Ainesosa("sipuli"));
      ainesosaRepo.save(new Ainesosa("valkosipuli"));
      ainesosaRepo.save(new Ainesosa("basilika"));
      ainesosaRepo.save(new Ainesosa("kananmuna"));
      ainesosaRepo.save(new Ainesosa("pippuri"));
      ainesosaRepo.save(new Ainesosa("suola"));
      ainesosaRepo.save(new Ainesosa("parmesaani"));
    };
  }

}
