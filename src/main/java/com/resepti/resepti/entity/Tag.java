package com.resepti.resepti.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tag")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long tagId;

  @NotBlank(message = "Tagin nimi on pakollinen")
  @Column(name = "nimi", nullable = false, unique = true)
  private String nimi;

  @JsonIgnore
  @ManyToMany(mappedBy = "tags")
  private Set<Resepti> reseptit = new HashSet<>();

  public Tag() {}

  public Tag(String nimi) {
    this.nimi = nimi;
  }

  public void addResepti(Resepti resepti) {
    reseptit.add(resepti);
    if (!resepti.getTags().contains(this)) {
        resepti.getTags().add(this);
    }
  }

  public void removeResepti(Resepti resepti) {
      reseptit.remove(resepti);
      resepti.getTags().remove(this);
  }

  public void setReseptit(Set<Resepti> reseptit) {
    this.reseptit = reseptit;
  }

  public Set<Resepti> getReseptit() {
    return reseptit;
  }

  public Long getTagId() {
    return tagId;
  }

  public void setTagId(Long tagId) {
    this.tagId = tagId;
  }

  public String getNimi() {
    return nimi;
  }

  public void setNimi(String nimi) {
    this.nimi = nimi;
  }

  @Override
  public String toString() {
    return "Tag [tagId=" + tagId + ", nimi=" + nimi + "]";
  }


}
