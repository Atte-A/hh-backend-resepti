package com.resepti.resepti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.entity.ReseptiAines;
import java.util.List;
import java.util.Optional;


public interface ReseptiAinesRepo extends JpaRepository<ReseptiAines, Long> {
  List<ReseptiAines> findByResepti(Resepti resepti);
  Optional<ReseptiAines> findByResepti_ReseptiIdAndAinesosa_AinesosaId(Long reseptiId, Long ainesosaId);
}
