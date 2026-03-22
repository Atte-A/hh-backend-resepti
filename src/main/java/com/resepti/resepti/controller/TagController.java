package com.resepti.resepti.controller;

import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestBody;

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
  @PostMapping("/{id}")
  public String poistaTagi(@PathVariable Long id) {
    if (!tagRepo.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tagia ei löytynyt id:llä " + id);
    }

    tagRepo.deleteById(id);
    return "redirect:/tagit";
  }

}
