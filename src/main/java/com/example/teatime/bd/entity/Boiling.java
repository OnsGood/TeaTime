package com.example.teatime.bd.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "teatime", name = "boiling")
public class Boiling {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  private String title;
  private String description;
  @Column(nullable = false)
  private Boolean active;
  @ManyToOne(targetEntity = Tea.class)
  @JoinColumn(name = "tea", nullable = true)
  private Tea tea;
  @OneToMany(targetEntity = BoilingElement.class)
  private List<BoilingElement> boilingElementList;

  public Boiling() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Tea getTea() {
    return tea;
  }

  public void setTea(Tea tea) {
    this.tea = tea;
  }

  public List<BoilingElement> getBoilingElementList() {
    return boilingElementList;
  }

  public void setBoilingElementList(List<BoilingElement> boilingElementList) {
    this.boilingElementList = boilingElementList;
  }
}
