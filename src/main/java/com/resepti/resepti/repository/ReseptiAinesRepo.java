package com.resepti.resepti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.entity.ReseptiAines;
import java.util.List;


public interface ReseptiAinesRepo extends JpaRepository<ReseptiAines, Long> {
  List<ReseptiAines> findByResepti(Resepti resepti);
}
