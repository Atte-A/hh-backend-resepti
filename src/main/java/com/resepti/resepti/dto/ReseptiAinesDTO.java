package com.resepti.resepti.dto;

public class ReseptiAinesDTO {
  private Long reseptiId;
  private Long ainesosaId;
  private Double maara;
  private String yksikko;

  public ReseptiAinesDTO() {}

  public Long getReseptiId() {
    return reseptiId;
  }

  public void setReseptiId(Long reseptiId) {
    this.reseptiId = reseptiId;
  }

  public Long getAinesosaId() {
    return ainesosaId;
  }

  public void setAinesosaId(Long ainesosaId) {
    this.ainesosaId = ainesosaId;
  }

  public Double getMaara() {
    return maara;
  }

  public void setMaara(Double maara) {
    this.maara = maara;
  }

  public String getYksikko() {
    return yksikko;
  }

  public void setYksikko(String yksikko) {
    this.yksikko = yksikko;
  }

  
}
