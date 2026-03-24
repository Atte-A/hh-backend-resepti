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
  @Column(name = "kayttaja_id")
  private Long kayttajaId;
  
  @NotBlank
  @Column(name = "kayttajatunnus", nullable = false, unique = true)
  private String kayttajatunnus;

  @NotBlank
  @Column(name = "salasana_hashed")
  private String salasanaHashed;

  @NotBlank
  @Column(nullable = false)
  private String rooli;
  
  public Kayttaja() {}

  public Kayttaja(String kayttajatunnus, String salasanaHashed, String rooli) {
    this.kayttajatunnus = kayttajatunnus;
    this.salasanaHashed = salasanaHashed;
    this.rooli = rooli;
  }

  public Long getKayttajaId() {
    return kayttajaId;
  }

  public void setKayttajaId(Long kayttajaId) {
    this.kayttajaId = kayttajaId;
  }

  public String getKayttajatunnus() {
    return kayttajatunnus;
  }

  public void setKayttajatunnus(String kayttajatunnus) {
    this.kayttajatunnus = kayttajatunnus;
  }

  public String getSalasanaHashed() {
    return salasanaHashed;
  }

  public void setSalasanaHashed(String salasanaHashed) {
    this.salasanaHashed = salasanaHashed;
  }

  public String getRooli() {
    return rooli;
  }

  public void setRooli(String rooli) {
    this.rooli = rooli;
  }

  @Override
  public String toString() {
    return "Kayttaja [kayttajaId=" + kayttajaId + ", kayttajatunnus=" + kayttajatunnus + ", salasanaHashed="
        + salasanaHashed + ", rooli=" + rooli + "]";
  }

}
