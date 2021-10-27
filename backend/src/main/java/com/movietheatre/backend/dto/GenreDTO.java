package com.movietheatre.backend.dto;

import java.io.Serializable;

public class GenreDTO implements Serializable {
  Long id;
  String genreName;

  public GenreDTO(Long id, String genreName) {
    this.id = id;
    this.genreName = genreName;
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

  @Override
  public String toString() {
    return "GenreDTO{" +
      "id=" + id +
      ", genreName='" + genreName + '\'' +
      '}';
  }
}
