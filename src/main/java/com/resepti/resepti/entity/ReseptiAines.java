package com.resepti.resepti.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "resepti_aines")
public class ReseptiAines {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reseptiAinesId;

  @ManyToOne
  @JoinColumn(name = "resepti_id", nullable = false)
  private Resepti resepti;

  @ManyToOne
  @JoinColumn(name = "ainesosa_id", nullable = false)
  private Ainesosa ainesosa;

  @Column(name = "maara")
  private double maara;

  @Column(name = "yksikko")
  private String yksikko;

  public ReseptiAines() {}

  public ReseptiAines(Resepti resepti, Ainesosa ainesosa, double maara, String yksikko) {
    this.resepti = resepti;
    this.ainesosa = ainesosa;
    this.maara = maara;
    this.yksikko = yksikko;
  }

  public Long getReseptiAinesId() {
    return reseptiAinesId;
  }

  public void setReseptiAinesId(Long reseptiAinesId) {
    this.reseptiAinesId = reseptiAinesId;
  }

  public Resepti getResepti() {
    return resepti;
  }

  public void setResepti(Resepti resepti) {
    this.resepti = resepti;
  }

  public Ainesosa getAinesosa() {
    return ainesosa;
  }

  public void setAinesosa(Ainesosa ainesosa) {
    this.ainesosa = ainesosa;
  }

  public double getMaara() {
    return maara;
  }

  public void setMaara(double maara) {
    this.maara = maara;
  }

  public String getYksikko() {
    return yksikko;
  }

  public void setYksikko(String yksikko) {
    this.yksikko = yksikko;
  }

  @Override
  public String toString() {
    return "ReseptiAinesosa [reseptiAinesId=" + reseptiAinesId + ", resepti=" + resepti + ", ainesosa=" + ainesosa
        + ", maara=" + maara + ", yksikko=" + yksikko + "]";
  }

}
