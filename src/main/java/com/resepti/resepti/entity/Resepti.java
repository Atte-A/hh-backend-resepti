package com.resepti.resepti.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "resepti")
public class Resepti {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "resepti_id")
  private Long reseptiId;

  @NotBlank(message = "Nimi on pakollinen")
  @Size(min = 3, max = 50, message = "Nimi pitää olla 3 - 50 merkkiä pitkä")
  @Column(name = "nimi", nullable = false, unique = true)
  private String nimi;

  @Size(max = 100, message = "Kuvaus saa olla enintää 100 merkkiä")
  @Column(name = "kuvaus")
  private String kuvaus;

  @NotBlank(message = "Ohje on pakollinen")
  @Column(name = "ohje", nullable = false)
  private String ohje;

  @Size(max = 1440, message = "Valmistusaika ei saa ylittää 24 tuntia")
  @Column(name = "valmistusaika")
  private Integer valmistusaika;

  @Size(min = 1, message = "Lisää annosmääräksi vähintään 1 annos")
  @Column(name = "annosmaara")
  private Integer annosmaara;

  @OneToMany(mappedBy = "resepti", cascade = CascadeType.ALL)
  private List<ReseptiKategoria> kategoriat = new ArrayList<>();

  @OneToMany(mappedBy = "resepti", cascade = CascadeType.ALL)
  private List<ReseptiAines> ainekset = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "resepti_tag",
    joinColumns = @JoinColumn(name = "resepti_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  private Set<Tag> tags = new HashSet<>();

  public Resepti() {}

  public Resepti(String nimi, String kuvaus, String ohje, Integer valmistusaika, Integer annosmaara) {
    this.nimi = nimi;
    this.kuvaus = kuvaus;
    this.ohje = ohje;
    this.valmistusaika = valmistusaika;
    this.annosmaara = annosmaara;
  }

  public void addTag(Tag tag) {
    this.tags.add(tag);
    tag.getReseptit().add(this);
  }

  public void removeTag(Tag tag) {
    this.tags.remove(tag);
    tag.getReseptit().remove(this);
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

  public List<ReseptiKategoria> getKategoriat() {
    return kategoriat;
  }

  public void setKategoriat(List<ReseptiKategoria> kategoriat) {
    this.kategoriat = kategoriat;
  }

  public List<ReseptiAines> getAinekset() {
    return ainekset;
  }

  public void setAinekset(List<ReseptiAines> ainekset) {
    this.ainekset = ainekset;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return "Resepti [reseptiId=" + reseptiId + ", nimi=" + nimi + ", kuvaus=" + kuvaus + ", ohje=" + ohje
        + ", valmistusaika=" + valmistusaika + ", annosmaara=" + annosmaara + ", kategoriat=" + kategoriat
        + ", ainekset=" + ainekset + ", tags=" + tags + "]";
  }

}
