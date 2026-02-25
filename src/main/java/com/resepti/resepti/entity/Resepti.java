package com.resepti.resepti.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "resepti")
public class Resepti {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "resepti_id")
  private Long reseptiId;

  @Column(name = "nimi", nullable = false)
  private String nimi;

  @Column(name = "kuvaus")
  private String kuvaus;

  @Column(name = "ohje", nullable = false)
  private String ohje;

  @Column(name = "valmistusaika")
  private Integer valmistusaika;

  @Column(name = "annosmaara")
  private Integer annosmaara;

  @OneToMany(mappedBy = "resepti", cascade = CascadeType.ALL)
  private List<ReseptiAines> ainekset = new ArrayList<>();

  public Resepti() {}

  public Resepti(String nimi, String kuvaus, String ohje, Integer valmistusaika, Integer annosmaara) {
    this.nimi = nimi;
    this.kuvaus = kuvaus;
    this.ohje = ohje;
    this.valmistusaika = valmistusaika;
    this.annosmaara = annosmaara;
  }

  public Long getReseptiId() {
    return reseptiId;
  }

  public void setReseptiId(Long reseptiId) {
    this.reseptiId = reseptiId;
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

  public String getOhje() {
    return ohje;
  }

  public void setOhje(String ohje) {
    this.ohje = ohje;
  }

  public Integer getValmistusaika() {
    return valmistusaika;
  }

  public void setValmistusaika(Integer valmistusaika) {
    this.valmistusaika = valmistusaika;
  }

  public Integer getAnnosmaara() {
    return annosmaara;
  }

  public void setAnnosmaara(Integer annosmaara) {
    this.annosmaara = annosmaara;
  }

  @Override
  public String toString() {
    return "Resepti [reseptiId=" + reseptiId + ", nimi=" + nimi + ", kuvaus=" + kuvaus + ", ohje=" + ohje
        + ", valmistusaika=" + valmistusaika + ", annosmaara=" + annosmaara + "]";
  }

}
