package com.example.teatime.bd.entity;

import javax.persistence.*;

@Entity
@Table(schema = "teatime", name = "moderator")
public class Moderator {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  private Long tgId;

  public Moderator() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getTgId() {
    return tgId;
  }

  public void setTgId(Long tgId) {
    this.tgId = tgId;
  }
}
