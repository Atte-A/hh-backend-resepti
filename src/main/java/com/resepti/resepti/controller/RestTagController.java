package com.resepti.resepti.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import com.resepti.resepti.entity.Tag;
import com.resepti.resepti.repository.TagRepo;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tagit")
public class RestTagController {

  private final TagRepo tagRepo;

  public RestTagController(TagRepo tagRepo) {
    this.tagRepo = tagRepo;
  }

  // Hae kaikki tagit
  @GetMapping({ "", "/" })
  public List<Tag> haeTagit() {
    return tagRepo.findAll();
  }

  // Hae yksittäinen tag id:llä
  @GetMapping("/{id}")
  public Optional<Tag> haeTag(@PathVariable Long id) {
    return tagRepo.findById(id);
  }

  // Luo uusi tag
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Tag luoTag(@Valid @RequestBody Tag uusiTag) {
    return tagRepo.save(uusiTag);
  }

  // Muokkaa tagia
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public Tag muokkaaTag(@PathVariable Long id, @Valid @RequestBody Tag muokattuTag) {
    muokattuTag.setTagId(id);
    return tagRepo.save(muokattuTag);
  }

  // Poista tag
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public void poistaTag(@PathVariable Long id) {
    tagRepo.deleteById(id);
  }
}