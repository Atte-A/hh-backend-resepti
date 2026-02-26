package com.resepti.resepti.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resepti.resepti.repository.ReseptiRepo;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/reseptit")
public class ReseptiController {

  // private final ReseptiRepo reseptiRepo;

  @GetMapping
  public String hello() {
      return "Hello World!";
  }
  
}
