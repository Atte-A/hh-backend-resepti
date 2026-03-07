package com.resepti.resepti.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.entity.ReseptiAines;
import com.resepti.resepti.repository.AinesosaRepo;
import com.resepti.resepti.repository.ReseptiAinesRepo;
import com.resepti.resepti.repository.ReseptiRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/resepti-ainekset")
public class ReseptiAinesController {

  private final ReseptiAinesRepo reseptiAinesosaRepo;
  private final ReseptiRepo reseptiRepo;
  private final AinesosaRepo ainesosaRepo;

  public ReseptiAinesController(ReseptiAinesRepo reseptiAinesosaRepo, ReseptiRepo reseptiRepo, AinesosaRepo ainesosaRepo) {
    this.reseptiAinesosaRepo = reseptiAinesosaRepo;
    this.reseptiRepo = reseptiRepo;
    this.ainesosaRepo = ainesosaRepo;
  }

  // Hakee tietyn reseptin ainekset
  @GetMapping("/{id}")
  public Optional<ReseptiAines> haeAinekset(@PathVariable Long id) {
      reseptiRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

      return reseptiAinesosaRepo.findById(id);
  }


}
