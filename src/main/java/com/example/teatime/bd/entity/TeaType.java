package com.example.teatime.bd.entity;

import javax.persistence.*;

@Entity
@Table(schema = "teatime", name = "tea_type")
public class TeaType {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  private String title;
  private String description;
  @Column(nullable = false)
  private Boolean active;

  public TeaType() {
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

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
