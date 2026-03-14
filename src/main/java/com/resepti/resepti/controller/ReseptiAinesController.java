package com.resepti.resepti.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.resepti.resepti.dto.ReseptiAinesDTO;
import com.resepti.resepti.entity.Ainesosa;
import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.entity.ReseptiAines;
import com.resepti.resepti.repository.AinesosaRepo;
import com.resepti.resepti.repository.ReseptiAinesRepo;
import com.resepti.resepti.repository.ReseptiRepo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/resepti-ainekset")
public class ReseptiAinesController {

  private final ReseptiAinesRepo reseptiAinesosaRepo;
  private final ReseptiRepo reseptiRepo;
  private final AinesosaRepo ainesosaRepo;

  public ReseptiAinesController(ReseptiAinesRepo reseptiAinesosaRepo, ReseptiRepo reseptiRepo,
      AinesosaRepo ainesosaRepo) {
    this.reseptiAinesosaRepo = reseptiAinesosaRepo;
    this.reseptiRepo = reseptiRepo;
    this.ainesosaRepo = ainesosaRepo;
  }

  // Hakee tietyn reseptin ainekset
  @GetMapping("/{reseptiId}")
  public List<ReseptiAines> haeAinekset(@PathVariable Long reseptiId) {
    Resepti resepti = reseptiRepo.findById(reseptiId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Reseptiä ei löytynyt resepti_id:llä " + reseptiId));

    return reseptiAinesosaRepo.findByResepti(resepti);
  }

  // Lisää tietyn ainesosan tiettyyn reseptiin
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ReseptiAines lisaaReseptiAines(@RequestBody ReseptiAinesDTO request) {

    Long reseptiId = request.getReseptiId();
    Long ainesosaId = request.getAinesosaId();

    Resepti resepti = reseptiRepo.findById(reseptiId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Reseptiä ei löytynyt resepti_id:llä " + reseptiId));
    Ainesosa ainesosa = ainesosaRepo.findById(ainesosaId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Ainesosaa ei löytynyt ainesosa_id:llä " + ainesosaId));

    ReseptiAines uusi = new ReseptiAines(resepti, ainesosa, request.getMaara(), request.getYksikko());

    return reseptiAinesosaRepo.save(uusi);
  }

}
