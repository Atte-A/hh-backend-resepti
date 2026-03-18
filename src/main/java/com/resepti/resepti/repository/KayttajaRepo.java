package com.resepti.resepti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resepti.resepti.entity.Kayttaja;

public interface KayttajaRepo extends JpaRepository<Kayttaja, Long> {

  Optional<Kayttaja> findByKayttajatunnus(String kayttajatunnus);

}
