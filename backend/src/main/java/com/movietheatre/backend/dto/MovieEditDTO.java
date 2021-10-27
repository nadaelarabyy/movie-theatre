package com.movietheatre.backend.dto;

import com.movietheatre.backend.entities.Genre;

import java.io.Serializable;
import java.util.List;

public class MovieEditDTO implements Serializable {
  List<GenreDTO> genres;
  String releaseDate;
  String lang;

  public MovieEditDTO(List<GenreDTO> genres, String releaseDate, String lang) {
    this.genres = genres;
    this.releaseDate = releaseDate;
    this.lang = lang;
  }

  public List<GenreDTO> getGenres() {
    return genres;
  }

  public void setGenres(List<GenreDTO> genres) {
    this.genres = genres;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }
}
