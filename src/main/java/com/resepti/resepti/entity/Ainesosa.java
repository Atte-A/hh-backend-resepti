package com.resepti.resepti.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ainesosa")
public class Ainesosa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ainesosa_id")
  private Long ainesosaId;
  
  @NotBlank(message = "Aineosan nimi on pakollinen")
  @Column(name = "nimi", nullable = false, unique = true)
  private String nimi;

  @JsonIgnore
  @OneToMany(mappedBy = "ainesosa")
  private Set<ReseptiAines> reseptit = new HashSet<>();

  public Ainesosa() {}

  public Ainesosa(String nimi) {
    this.nimi = nimi;
  }

  public void addReseptiAines(ReseptiAines reseptiAines) {
    reseptit.add(reseptiAines);
    reseptiAines.setAinesosa(this);
}

public void removeReseptiAines(ReseptiAines reseptiAines) {
    reseptit.remove(reseptiAines);
    reseptiAines.setAinesosa(null);
}

  public Long getAinesosaId() {
    return ainesosaId;
  }

  public void setAinesosaId(Long ainesosaId) {
    this.ainesosaId = ainesosaId;
  }

  public String getNimi() {
    return nimi;
  }

  public void setNimi(String nimi) {
    this.nimi = nimi;
  }

  @Override
  public String toString() {
    return "Ainesosa [ainesosaId=" + ainesosaId + ", nimi=" + nimi + "]";
  }
  
}
