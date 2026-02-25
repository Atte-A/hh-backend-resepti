package com.resepti.resepti.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "kategoria")
public class Kategoria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "kategoria_id")
  private Long kategoriaId;

  @Column(name = "nimi")
  private String nimi;

  @Column(name = "kuvaus")
  private String kuvaus;

  @OneToMany(mappedBy = "kategoria")
  private List<ReseptiKategoria> reseptit = new ArrayList<>();

  public Kategoria() {}

  public Kategoria(String nimi, String kuvaus) {
    this.nimi = nimi;
    this.kuvaus = kuvaus;
  }

  public Long getKategoriaId() {
    return kategoriaId;
  }

  public void setKategoriaId(Long kategoriaId) {
    this.kategoriaId = kategoriaId;
  }

  public String getNimi() {
    return nimi;
  }

  public void setNimi(String nimi) {
    this.nimi = nimi;
  }

  public String getKuvaus() {
    return kuvaus;
  }

  public void setKuvaus(String kuvaus) {
    this.kuvaus = kuvaus;
  }

  @Override
  public String toString() {
    return "Kategoria [kategoriaId=" + kategoriaId + ", nimi=" + nimi + ", kuvaus=" + kuvaus + "]";
  }

}
