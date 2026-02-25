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
@Table(name = "ainesosa")
public class Ainesosa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ainesosa_id")
  private Long ainesosaId;
  
  @Column(name = "nimi")
  private String nimi;

  @OneToMany(mappedBy = "ainesosa")
  private List<ReseptiAines> reseptit = new ArrayList<>();

  public Ainesosa() {}

  public Ainesosa(String nimi) {
    this.nimi = nimi;
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
