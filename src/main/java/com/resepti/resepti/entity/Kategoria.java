package com.resepti.resepti.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "kategoria")
public class Kategoria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "kategoria_id")
  private Long kategoriaId;

  @NotBlank(message = "Nimi on pakollinen")
  @Size(max = 50, message = "Nimi ei saa olla yli 50 merkkiä")
  @Column(name = "nimi")
  private String nimi;


  @OneToMany(mappedBy = "kategoria")
  private Set<ReseptiKategoria> reseptit = new HashSet<>();

  public Kategoria() {}

  public Kategoria(String nimi) {
    this.nimi = nimi;
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


  @Override
  public String toString() {
    return "Kategoria [kategoriaId=" + kategoriaId + ", nimi=" + nimi + "]";
  }

}
