package com.resepti.resepti.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.repository.ReseptiRepo;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/reseptit")
public class ReseptiController {

  private final ReseptiRepo reseptiRepo;

  public ReseptiController(ReseptiRepo reseptiRepo) {
    this.reseptiRepo = reseptiRepo;
  }

  @GetMapping
  public String haeReseptit(Model model) {
    model.addAttribute("reseptit", reseptiRepo.findAll());
    return "reseptit";
  }

  @GetMapping("/{id}")
  public String haeResepti(@PathVariable Long id, Model model) {
    Resepti resepti = reseptiRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseptiä ei löytynyt id:llä " + id));
    model.addAttribute("resepti", resepti);
    return "resepti";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public String lisaaResepti(@Valid Resepti resepti, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("reseptit", reseptiRepo.findAll());
      return "reseptit";
    }
    reseptiRepo.save(resepti);
    return "redirect:/reseptit";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/muokkaa/{id}")
  public String naytaMuokkaaResepti(@PathVariable Long id, Model model) {
    Resepti resepti = reseptiRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseptiä ei löytynyt id:llä " + id));
    model.addAttribute("resepti", resepti);
    return "muokkaaResepti";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/muokkaa/{id}")
  public String tallennaMuokattuResepti(@PathVariable Long id, @Valid Resepti resepti, BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      model.addAttribute("resepti", resepti);
      return "muokkaaResepti";
    }
    resepti.setReseptiId(id);
    reseptiRepo.save(resepti);
    return "redirect:/reseptit";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/poista/{id}")
  public String poistaResepti(@PathVariable Long id) {
    if (!reseptiRepo.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseptiä ei löytynyt id:llä " + id);
    }
    reseptiRepo.deleteById(id);
    return "redirect:/reseptit";
  }
}