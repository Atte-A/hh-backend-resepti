package com.resepti.resepti;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.repository.ReseptiRepo;

@SpringBootApplication
public class ReseptiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReseptiApplication.class, args);
	}

  @Bean
  CommandLineRunner demo(ReseptiRepo reseptiRepo) {
    return (args) -> {
      reseptiRepo.save(new Resepti("Pasta Carbonara", "Herkullinen arjen pelasta", "1. Keitä pasta, 2. Tee kastike", 85, 4));
      reseptiRepo.save(new Resepti("Pannukakku", "Maailman herkullisin pannari", "1. Sekoita kuiva aineet, 2. Lisää maito ja kananmunat", 45,5));
    };
  }

}
