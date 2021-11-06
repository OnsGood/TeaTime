package com.example.teatime.bd.entity;

import javax.persistence.*;

@Entity
@Table(schema = "teatime", name = "boiling_element")
public class BoilingElement {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  private Boolean active;
  @Column(nullable = false)
  private Long seconds;
  @Column(nullable = false)
  private Long temperature;
  @Column(nullable = false)
  private Long mass;
  @Column(nullable = false)
  private Long number;
  @ManyToOne(targetEntity = Boiling.class)
  private Boiling boiling;

  public BoilingElement() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Long getSeconds() {
    return seconds;
  }

  public void setSeconds(Long seconds) {
    this.seconds = seconds;
  }

  public Long getTemperature() {
    return temperature;
  }

  public void setTemperature(Long temperature) {
    this.temperature = temperature;
  }

  public Long getNumber() {
    return number;
  }

  public void setNumber(Long number) {
    this.number = number;
  }

  public Boiling getBoiling() {
    return boiling;
  }

  public void setBoiling(Boiling boiling) {
    this.boiling = boiling;
  }

  public Long getMass() {
    return mass;
  }

  public void setMass(Long mass) {
    this.mass = mass;
  }
}
