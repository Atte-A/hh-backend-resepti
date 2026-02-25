package com.resepti.resepti.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "resepti_kateogia")
public class ReseptiKategoria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reseptiKategoriaId;

  @ManyToOne
  @JoinColumn(name = "resepti_id", nullable = false)
  private Resepti resepti;

  @ManyToOne
  @JoinColumn(name = "kategoria_id", nullable = false)
  private Kategoria kategoria;

  public ReseptiKategoria() {}

  public ReseptiKategoria(Resepti resepti, Kategoria kategoria) {
    this.resepti =resepti;
    this.kategoria = kategoria;
  }

  public Long getReseptiKategoriaId() {
    return reseptiKategoriaId;
  }

  public void setReseptiKategoriaId(Long reseptiKategoriaId) {
    this.reseptiKategoriaId = reseptiKategoriaId;
  }

  public Resepti getResepti() {
    return resepti;
  }

  public void setResepti(Resepti resepti) {
    this.resepti = resepti;
  }

  public Kategoria getKategoria() {
    return kategoria;
  }

  public void setKategoria(Kategoria kategoria) {
    this.kategoria = kategoria;
  }

}
