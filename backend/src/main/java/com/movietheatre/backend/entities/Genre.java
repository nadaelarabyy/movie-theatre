package com.movietheatre.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Genre {
  @Id
  private Long id;
  private String genreName;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "movies_genres",
    joinColumns = @JoinColumn(name = "genre_id"),
    inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private Set<Movie> movies;

  public Genre() {
  }

  public Genre(String genreName, Set<Movie> movies) {
    this.genreName = genreName;
    this.movies = movies;
  }

  public Genre(Long id, String genreName, Set<Movie> movies) {
    this.id = id;
    this.genreName = genreName;
    this.movies = movies;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getGenreName() {
    return genreName;
  }

  public void setGenreName(String genreName) {
    this.genreName = genreName;
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
    return "Genre{" +
      "id=" + id +
      ", genreName='" + genreName + '\'' +
      ", movies=" + movies +
      '}';
  }
}
