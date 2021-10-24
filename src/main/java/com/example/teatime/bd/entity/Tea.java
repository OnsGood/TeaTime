package com.example.teatime.bd.entity;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@Table(schema = "teatime", name = "tea")
public class Tea {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  private String title;
  private String description;
  @ManyToOne(targetEntity = TeaType.class)
  @JoinColumn(name = "tea_type", nullable = false)
  private TeaType teaType;
  @Column(nullable = false)
  private Boolean active;

  public Tea() {
  }

  public Tea(Long id, String title, String description) {
    this.id = id;
    this.title = title;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TeaType getTeaType() {
    return teaType;
  }

  public void setTeaType(TeaType teaType) {
    this.teaType = teaType;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Tea.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("title='" + title + "'")
        .add("description='" + description + "'")
        .add("teaType=" + teaType)
        .add("active=" + active)
        .toString();
  }
}
