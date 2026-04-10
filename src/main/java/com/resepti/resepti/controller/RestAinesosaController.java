package com.resepti.resepti.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.resepti.resepti.entity.Ainesosa;
import com.resepti.resepti.repository.AinesosaRepo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/ainesosat")
public class RestAinesosaController {

  private final AinesosaRepo ainesosaRepo;

  public RestAinesosaController(AinesosaRepo ainesosaRepo) {
    this.ainesosaRepo = ainesosaRepo;
  }

  // Hae kaikki ainesosat
  @GetMapping({"", "/"})
  public List <Ainesosa> haeAinesosat() {
      return ainesosaRepo.findAll();
  }

  // Hae ainesosa ainesosa_id:llä
  @GetMapping("/{id}")
  public Ainesosa haeAinesosa(@PathVariable Long id) {
      return ainesosaRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ainesosaa ei löytynyt id:llä " + id));
  }

  // Lisää uusi ainesosa
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Ainesosa lisaaAinesosa(@RequestBody Ainesosa uusiAinesosa) {
      return ainesosaRepo.save(uusiAinesosa);
  }

  // Muokkaa ainesosaa
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public Ainesosa muokkaaAinesosa(@PathVariable Long id, @RequestBody Ainesosa muokattuAinesosa) {
    ainesosaRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aineosaa ei lötynyt id:llä " + id));
    muokattuAinesosa.setAinesosaId(id);
    return ainesosaRepo.save(muokattuAinesosa);
  }

  // Poista ainesosa
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void poistaAinesosa(@PathVariable Long id) {
    ainesosaRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ainesosaa ei löytynyt id:llä " + id));
    ainesosaRepo.deleteById(id);
  }
  
}
