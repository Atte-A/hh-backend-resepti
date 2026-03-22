package com.resepti.resepti.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.resepti.resepti.entity.Ainesosa;
import com.resepti.resepti.repository.AinesosaRepo;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/ainesosat")
public class AinesosaController {

  private final AinesosaRepo ainesosaRepo;

  public AinesosaController(AinesosaRepo ainesosaRepo) {
    this.ainesosaRepo = ainesosaRepo;
  }

  // Hakee ja näyttää kaikki ainesosat
  @GetMapping
  public String haeAinekset(Model model) {
    model.addAttribute("ainesosat", ainesosaRepo.findAll());
    return "ainesosat";
  }

  // Näyttää lomakkeen uuden ainesosan lisäämiseksi
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/lisaa")
  public String naytaUusiAinesosa(Model model) {
    model.addAttribute("ainesosa", new Ainesosa());

    return "lisaaAinesosa";
  }

  // Tallentaa uuden ainesosan
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/lisaa")
  public String luoUusiAinesosa(@Valid Ainesosa ainesosa, BindingResult result) {

    if (ainesosaRepo.findByNimi(ainesosa.getNimi()).isPresent()) {
    result.rejectValue("nimi", "error.ainesosa", "Ainesosa on jo olemassa");
    }

    if (result.hasErrors()) {
      return "lisaaAinesosa";
    }

    ainesosaRepo.save(ainesosa);
    return "redirect:/ainesosat";
  }

  // Poistaa ainesosan
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}")
  public String poistaAinesosa(@PathVariable Long id) {
    if (!ainesosaRepo.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ainesosaa ei löytynyt id:llä " + id);
    }

    ainesosaRepo.deleteById(id);
    return "redirect:/ainesosat";
  }

}
