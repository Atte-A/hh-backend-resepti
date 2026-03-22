package com.resepti.resepti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resepti.resepti.entity.Ainesosa;

public interface AinesosaRepo extends JpaRepository<Ainesosa, Long> {

  Optional<Ainesosa> findByNimi(String nimi);

}
