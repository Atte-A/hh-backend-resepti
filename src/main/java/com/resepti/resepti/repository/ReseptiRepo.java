package com.resepti.resepti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resepti.resepti.entity.Resepti;

public interface ReseptiRepo extends JpaRepository<Resepti, Long> {

  Optional<Resepti> findByNimi(String nimi);

}
