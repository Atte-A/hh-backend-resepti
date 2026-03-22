package com.resepti.resepti.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.resepti.resepti.entity.Ainesosa;
import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.entity.ReseptiAines;
import com.resepti.resepti.repository.AinesosaRepo;
import com.resepti.resepti.repository.ReseptiRepo;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/reseptit")
public class ReseptiController {

  private final ReseptiRepo reseptiRepo;
  private final AinesosaRepo ainesosaRepo;

  public ReseptiController(ReseptiRepo reseptiRepo, AinesosaRepo ainesosaRepo) {
    this.reseptiRepo = reseptiRepo;
    this.ainesosaRepo = ainesosaRepo;
  }

  // Näyttää kaikki reseptit
  @GetMapping
  public String haeReseptit(Model model) {
    model.addAttribute("reseptit", reseptiRepo.findAll());
    return "reseptit";
  }

  // Näyttää yksittäisen reseptin
  @GetMapping("/{id}")
  public String haeResepti(@PathVariable Long id, Model model) {
    Resepti resepti = reseptiRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseptiä ei löytynyt id:llä " + id));
    model.addAttribute("resepti", resepti);
    return "resepti";
  }

  // Näyttää lomakkeen reseptin lisäämiseen
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/lisaa")
  public String naytaUusiResepti(Model model) {
    Resepti resepti = new Resepti();
    for (int i = 0; i < 10; i++) {
      resepti.getAinekset().add(new ReseptiAines());
    }
    model.addAttribute("resepti", resepti);
    model.addAttribute("ainesosat", ainesosaRepo.findAll());
    return "lisaaResepti";
  }

  // Tallentaa reseptin
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/lisaa")
  public String luoUusiResepti(@Valid Resepti resepti, BindingResult result, Model model) {
    if (result.hasErrors()) {
      return "lisaaResepti";
    }

    List<ReseptiAines> validAinekset = new ArrayList<>();
    for (ReseptiAines ra : resepti.getAinekset()) {
      if (ra.getAinesosa() == null || ra.getAinesosa().getAinesosaId() == null) {
        continue;
      }

      Ainesosa ainesosa = ainesosaRepo.findById(ra.getAinesosa().getAinesosaId())
          .orElseThrow(() -> new IllegalArgumentException(
              "Ainesosaa ei löytynyt ID:llä " + ra.getAinesosa().getAinesosaId()));

      ra.setAinesosa(ainesosa);
      ra.setResepti(resepti);
      validAinekset.add(ra);
    }

    resepti.setAinekset(validAinekset);

    reseptiRepo.save(resepti);
    return "redirect:/reseptit";
  }

  // Näyttää lomakkeen reseptin muokkaamiseen
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/muokkaa/{id}")
  public String naytaMuokkaaResepti(@PathVariable Long id, Model model) {
    Resepti resepti = reseptiRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseptiä ei löytynyt id:llä " + id));
    model.addAttribute("resepti", resepti);
    return "muokkaaResepti";
  }

  // Tallentaa muokatun reseptin
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
    return "redirect:/resepti";
  }

  // Poistaa reseptin
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