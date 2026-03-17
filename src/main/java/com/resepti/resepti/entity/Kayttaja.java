package com.resepti.resepti.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "kayttaja")
public class Kayttaja {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "kayttaja")
  private Long kayttajaId;
  
  @NotBlank
  private String kayttajatunnus;

  @NotBlank
  private String salasanaHashed;

  @NotBlank
  private String role;
}
