package com.resepti.resepti.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.resepti.resepti.entity.Tag;
import com.resepti.resepti.repository.TagRepo;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/tagit")
public class TagController {

  private final TagRepo tagRepo;

  public TagController(TagRepo tagRepo) {
    this.tagRepo = tagRepo;
  }

  // Hakee ja näyttää kaikki tagit
  @GetMapping
  public String haeTagit(Model model) {
    model.addAttribute("tagit", tagRepo.findAll());
    return "tagit";
  }

  // Lisää uuden tagin
  @GetMapping("/lisaa")
  public String naytaUusiTagi(Model model) {
    model.addAttribute("tag", new Tag());
    return "lisaaTagi";
  }

  // Tallentaa uuden tagin
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/lisaa")
  public String luoUusiTag(@Valid Tag tag, BindingResult result) {
    if (tagRepo.findByNimi(tag.getNimi()).isPresent()) {
      result.rejectValue("nimi", "error.tag", "Tagi on jo olemassa");
    }

    if (result.hasErrors()) {
      return "lisaaTagi";
    }

    tagRepo.save(tag);
    return "redirect:/tagit";
  }

  // Poistaa tagin
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}")
  public String poistaTagi(@PathVariable Long id, Model model) {
    if (!tagRepo.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tagia ei löytynyt id:llä " + id);
    }

    try {
      tagRepo.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      model.addAttribute("error", "Tagia ei voi poistaa, koska se on käytössä reseptissä");
      model.addAttribute("tagit", tagRepo.findAll());
      return "tagit";
    }

    return "redirect:/tagit";
  }

}
