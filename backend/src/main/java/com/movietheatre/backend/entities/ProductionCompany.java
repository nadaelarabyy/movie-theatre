package com.movietheatre.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class ProductionCompany {
  @Id
  private Long id;
  private String name;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "movies_production",
    joinColumns = @JoinColumn(name = "prod_id"),
    inverseJoinColumns = @JoinColumn(name = "movie_id")
  )
  private Set<Movie> movies;

  public ProductionCompany() {
  }

  public ProductionCompany(String name, Set<Movie> movies) {
    this.name = name;
    this.movies = movies;
  }

  public ProductionCompany(Long id, String name, Set<Movie> movies) {
    this.id = id;
    this.name = name;
    this.movies = movies;
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
  public Set<Movie> getMovies() {
    return movies;
  }

  public void setMovies(Set<Movie> movies) {
    this.movies = movies;
  }

  @Override
  public String toString() {
    return "ProductionCompany{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", movies=" + movies +
      '}';
  }
}

