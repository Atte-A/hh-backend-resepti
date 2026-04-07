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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/reseptit/{reseptiId}/ainesosat")
public class RestReseptiAinesController {

  private final ReseptiAinesRepo reseptiAinesosaRepo;
  private final ReseptiRepo reseptiRepo;
  private final AinesosaRepo ainesosaRepo;

  public RestReseptiAinesController(ReseptiAinesRepo reseptiAinesosaRepo, ReseptiRepo reseptiRepo,
      AinesosaRepo ainesosaRepo) {
    this.reseptiAinesosaRepo = reseptiAinesosaRepo;
    this.reseptiRepo = reseptiRepo;
    this.ainesosaRepo = ainesosaRepo;
  }

  // Hakee tietyn reseptin ainekset
  @GetMapping
  public List<ReseptiAines> haeAinekset(@PathVariable Long reseptiId) {

    Resepti resepti = reseptiRepo.findById(reseptiId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Reseptiä ei löytynyt resepti_id:llä " + reseptiId));

    return reseptiAinesosaRepo.findByResepti(resepti);
  }

  // Lisää tietyn ainesosan tiettyyn reseptiin
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ReseptiAines lisaaReseptiAines(@PathVariable Long reseptiId, @RequestBody ReseptiAinesDTO request) {

    Resepti resepti = reseptiRepo.findById(reseptiId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Reseptiä ei löytynyt resepti_id:llä " + reseptiId));
    Ainesosa ainesosa = ainesosaRepo.findById(request.getAinesosaId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Ainesosaa ei löytynyt ainesosa_id:llä " + request.getAinesosaId()));

    ReseptiAines uusi = new ReseptiAines(resepti, ainesosa, request.getMaara(), request.getYksikko());

    return reseptiAinesosaRepo.save(uusi);
  }

  // Muokkaa tietyn reseptin tiettyä ainesosaa
  @PutMapping("/{ainesosaId}")
  public ReseptiAines muokkaaReseptiAines(@PathVariable Long reseptiId, @PathVariable Long ainesosaId,
      @RequestBody ReseptiAinesDTO request) {

    ReseptiAines muokattava = reseptiAinesosaRepo
        .findByResepti_ReseptiIdAndAinesosa_AinesosaId(reseptiId, ainesosaId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"ReseptiAinesta ei löytynyt"));

    muokattava.setMaara(request.getMaara());
    muokattava.setYksikko(request.getYksikko());

    return reseptiAinesosaRepo.save(muokattava);
  }

  // Poistaa tietyn ainesosan tietystä reseptistä
  @DeleteMapping("/{ainesosaId}")
  public void poistaReseptiAines(@PathVariable Long reseptiId, @PathVariable Long ainesosaId) {
    
    ReseptiAines poistettava = reseptiAinesosaRepo.findByResepti_ReseptiIdAndAinesosa_AinesosaId(reseptiId, ainesosaId)
    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ReseptiAinesta ei löytynyt"));
    
    reseptiAinesosaRepo.delete(poistettava);
  }

}
