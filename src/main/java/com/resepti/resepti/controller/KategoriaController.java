package com.resepti.resepti.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.resepti.resepti.entity.Kategoria;
import com.resepti.resepti.repository.KategoriaRepo;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/kategoriat")
public class KategoriaController {

  private final KategoriaRepo kategoriaRepo;

  public KategoriaController(KategoriaRepo kategoriaRepo) {
    this.kategoriaRepo = kategoriaRepo;
  }

  // Hae kaikki kategoriat
  @GetMapping({"", "/"})
  public List<Kategoria> haeKategoriat() {
      return kategoriaRepo.findAll();
  }

  // Hae kategoria kategoria_id:llä
  @GetMapping("/{id}")
  public Kategoria haeKategoria(@PathVariable Long id) {
      return kategoriaRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategoriaa ei löytynyt id:llä " + id));
  }

  // Lisää uusi kategoria
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Kategoria lisaaKategoria(@Valid @RequestBody Kategoria uusiKategoria) {
      return kategoriaRepo.save(uusiKategoria);
  }

  // Muokkaa kategoriaa
  @PutMapping("/{id}")
  public Kategoria muokkaaKategoria(@PathVariable Long id, @Valid @RequestBody Kategoria muokattuKategoria) {
      kategoriaRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategoriaa ei löytynyt id:llä " + id));

      muokattuKategoria.setKategoriaId(id);
      return kategoriaRepo.save((muokattuKategoria));
  }

  // Poista kategoria
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void poistaKategoria(@PathVariable Long id) {
    kategoriaRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    kategoriaRepo.deleteById(id);
  }
  
}
