package com.resepti.resepti.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tag")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long tagId;

  @Column(name = "nimi")
  private String tag;

  @ManyToMany(mappedBy = "tags")
  private List<Resepti> reseptit = new ArrayList<>();

  public Tag() {}

  public Tag(String tag) {
    this.tag = tag;
  }
  
  public void setReseptit(List<Resepti> reseptit) {
    this.reseptit = reseptit;
  }

  public List<Resepti> getReseptit() {
    return reseptit;
  }

  public Long getTagId() {
    return tagId;
  }

  public void setTagId(Long tagId) {
    this.tagId = tagId;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  @Override
  public String toString() {
    return "Tag [tagId=" + tagId + ", tag=" + tag + "]";
  }

}
