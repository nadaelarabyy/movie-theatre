package com.movietheatre.backend.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table
public class StageName {
  @Id
  @SequenceGenerator(
    name = "stage_sequence",
    sequenceName = "stage_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "stage_sequence"
  )
  private Long id;
  private String name;
  @ManyToOne(fetch = FetchType.EAGER,optional = true,cascade = CascadeType.ALL)
  @JoinColumn(name = "cast_id",nullable = true)

  private Cast cast;

  public StageName() {
  }

  public StageName(String name, Cast cast) {
    this.name = name;
    this.cast = cast;
  }
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  @JsonBackReference
  public Cast getCast() {
    return cast;
  }

  public void setCast(Cast cast) {
    this.cast = cast;
  }

  @Override
  public String toString() {
    return "StageName{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", cast=" + cast +
      '}';
  }
}
