package com.resepti.resepti.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.resepti.resepti.entity.Ainesosa;
import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.entity.ReseptiAines;
import com.resepti.resepti.entity.Tag;
import com.resepti.resepti.repository.AinesosaRepo;
import com.resepti.resepti.repository.ReseptiRepo;
import com.resepti.resepti.repository.TagRepo;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/reseptit")
public class ReseptiController {

  private final ReseptiRepo reseptiRepo;
  private final AinesosaRepo ainesosaRepo;
  private final TagRepo tagRepo;

  public ReseptiController(ReseptiRepo reseptiRepo, AinesosaRepo ainesosaRepo, TagRepo tagRepo) {
    this.reseptiRepo = reseptiRepo;
    this.ainesosaRepo = ainesosaRepo;
    this.tagRepo = tagRepo;
  }

  // Näyttää kaikki reseptit
  @GetMapping
  public String haeReseptit(Model model) {
    model.addAttribute("reseptit", reseptiRepo.findAll());
    return "reseptit";
  }

  // Näyttää yksittäisen reseptin id:llä
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

    // Aineksien lisääminen, 10 ainesosariviä
    Resepti resepti = new Resepti();
    for (int i = 0; i < 10; i++) {
      resepti.getAinekset().add(new ReseptiAines());
    }

    model.addAttribute("resepti", resepti);
    model.addAttribute("ainesosat", ainesosaRepo.findAll());
    model.addAttribute("tagit", tagRepo.findAll());

    return "lisaaResepti";
  }

  // Tallentaa reseptin
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/lisaa")
  public String luoUusiResepti(@Valid Resepti resepti, BindingResult result,
      @RequestParam(required = false) List<Long> tagId, Model model) {

    // Jos tulee virhe, ladataan olemassa olevat tagit ja ainesosat uudestaan lomakkeelle
    if (result.hasErrors()) {
      model.addAttribute("tagit", tagRepo.findAll());
      model.addAttribute("ainesosat", ainesosaRepo.findAll());
      return "lisaaResepti";
    }

    // Lista lisättäville ainesosille
    List<ReseptiAines> reseptinAinekset = new ArrayList<>();

    // Käy ainesosat läpi, jos ainesosaa ei valita --> looppi jatkuu
    for (ReseptiAines ra : resepti.getAinekset()) {
      if (ra.getAinesosa() == null || ra.getAinesosa().getAinesosaId() == null) {
        continue;
      }

      // Haetaan oikea ainesosa tietokannasta
      Ainesosa ainesosa = ainesosaRepo.findById(ra.getAinesosa().getAinesosaId())
          .orElseThrow(() -> new IllegalArgumentException(
              "Ainesosaa ei löytynyt ID:llä " + ra.getAinesosa().getAinesosaId()));

      // Linkitetään olemassa oleva raaka-aine reseptiin
      ra.setAinesosa(ainesosa);
      ra.setResepti(resepti);
      reseptinAinekset.add(ra);
    }

    // Asetetaan valmis ainesosalista reseptiin
    resepti.setAinekset(reseptinAinekset);

    // Tagien käsittely
    if (tagId != null) {
      List<Tag> tags = tagRepo.findAllById(tagId);
      for (Tag tag : tags) {
        resepti.addTag(tag);
      }
    }

    try {
      reseptiRepo.save(resepti);
    } catch (DataIntegrityViolationException e) {
      // Tarkistetaan onko reseptin nimi duplikaatti
      result.rejectValue("nimi", "duplicate", "Resepti samalla nimellä on jo olemassa");
      model.addAttribute("tagit", tagRepo.findAll());
      model.addAttribute("ainesosat", ainesosaRepo.findAll());
      return "lisaaResepti";
    }

    return "redirect:/reseptit";
  }

  // Näyttää lomakkeen reseptin muokkaamiseen
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/muokkaa/{id}")
  public String naytaMuokkaaResepti(@PathVariable Long id, Model model) {

    // Tarkistetaan että muokattava resepti on olemassa
    Resepti resepti = reseptiRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseptiä ei löytynyt id:llä " + id));

    model.addAttribute("resepti", resepti);
    model.addAttribute("ainesosat", ainesosaRepo.findAll());
    model.addAttribute("tagit", tagRepo.findAll());

    return "lisaaResepti";
  }

  // Tallentaa muokatun reseptin
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/muokkaa/{id}")
  public String tallennaMuokattuResepti(@PathVariable Long id,
      @Valid Resepti resepti,
      BindingResult result,
      @RequestParam(required = false) List<Long> tagId,
      Model model) {

    // Hetaan muokattava resepti tietokannasta
    Resepti existing = reseptiRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    // Duplikaatin tarkistus
    String newName = resepti.getNimi();
    Optional<Resepti> existingWithName = reseptiRepo.findByNimi(newName);

    // Jos löytyy samalla nimellä, mutta eri id:llä --> duplikaatti!
    if (existingWithName.isPresent()
        && !existingWithName.get().getReseptiId().equals(id)) {
      result.rejectValue("nimi", "duplicate", "Resepti samalla nimellä on jo olemassa");
    }

    if (result.hasErrors()) {
      model.addAttribute("ainesosat", ainesosaRepo.findAll());
      model.addAttribute("tagit", tagRepo.findAll());
      return "lisaaResepti";
    }

    // Haetaan muokattavan reseptin tiedot
    existing.setNimi(resepti.getNimi());
    existing.setKuvaus(resepti.getKuvaus());
    existing.setOhje(resepti.getOhje());
    existing.setValmistusaika(resepti.getValmistusaika());
    existing.setAnnosmaara(resepti.getAnnosmaara());

    // Poistetaan vanhat ainesosarivit
    existing.getAinekset().clear();

    // Käydään läpi uusi lista
    for (ReseptiAines ra : resepti.getAinekset()) {
      // Skipataan käyttämättömät ainesosat
      if (ra.getAinesosa() == null || ra.getAinesosa().getAinesosaId() == null)
        continue;

      // Haetaan oikea Ainesosa entity tietokannasta
      Ainesosa a = ainesosaRepo.findById(ra.getAinesosa().getAinesosaId())
          .orElseThrow();

      // Linkitetään ainesosat reseptiin
      ra.setAinesosa(a);
      ra.setResepti(existing);
      // Luodaanlista uusilla ainesosilla
      existing.getAinekset().add(ra);
    }

    // Poistetaan vanhat tagit
    existing.getTags().clear();

    // Haetaan tagit oikeat tagit tietokannasta
    if (tagId != null) {
      for (Tag t : tagRepo.findAllById(tagId)) {
        existing.addTag(t);
      }
    }
    
    reseptiRepo.save(existing);

    return "redirect:/reseptit/" + id;
  }

  // Poistaa reseptin
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/poista/{id}")
  public String poistaResepti(@PathVariable Long id) {

    // Tarkistus löytyykö poistettava resepti
    if (!reseptiRepo.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseptiä ei löytynyt id:llä " + id);
    }
    reseptiRepo.deleteById(id);
    return "redirect:/reseptit";
  }
}