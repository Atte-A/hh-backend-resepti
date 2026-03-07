package com.resepti.resepti.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.resepti.resepti.repository.ReseptiRepo;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.resepti.resepti.entity.Resepti;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@Controller
@RequestMapping("/reseptit")
public class ReseptiController {

  private final ReseptiRepo reseptiRepo;

  public ReseptiController(ReseptiRepo reseptiRepo) {
    this.reseptiRepo = reseptiRepo;
  }

  // Hae kaikki reseptit tietokannasta
  @GetMapping({"", "/"})
  public List <Resepti> haeReseptit() {
    return reseptiRepo.findAll();
  }

  // Hae yksittäinen resepti resepti_id:llä
  @GetMapping("/{id}")
  public Optional <Resepti> haeResepti(@PathVariable Long id) {
    return reseptiRepo.findById(id);
  }

  // Luo uusi resepti
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Resepti luoResepti(@Valid @RequestBody Resepti uusiResepti) {
    return reseptiRepo.save(uusiResepti);
  }

  // Muokkaa reseptiä
  @PutMapping("/{id}")
  public Resepti muokkaaResepti(@PathVariable Long id, @Valid @RequestBody Resepti muokattuResepti) {
    muokattuResepti.setReseptiId(id);
    return reseptiRepo.save(muokattuResepti);
  }

  // Poista resepti
  @DeleteMapping("/{id}")
  public void poistaResepti(@PathVariable Long id) {
    reseptiRepo.deleteById(id);
  } 
  
}
