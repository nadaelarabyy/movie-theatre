package com.movietheatre.backend.dto;

import com.movietheatre.backend.entities.Genre;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class MovieDTO implements Serializable {
  private Long id;
  private String title;
  private String lang;
  private int movieLength;
  private String description;
  private double rating;
  private int likes;
  private String director;
  private String imagePath;

  private String releaseDate;
  private Set<GenreDTO> genres;

  public MovieDTO(Long id, String title, String lang, int movieLength, String description, double rating,
                  int likes, String director, String imagePath, String releaseDate,
                  Set<GenreDTO> genres) {
    this.id = id;
    this.title = title;
    this.lang = lang;
    this.movieLength = movieLength;
    this.description = description;
    this.rating = rating;
    this.likes = likes;
    this.director = director;
    this.imagePath = imagePath;
    this.releaseDate = releaseDate;
    this.genres = genres;
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

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  public int getMovieLength() {
    return movieLength;
  }

  public void setMovieLength(int movieLength) {
    this.movieLength = movieLength;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }


  public Set<GenreDTO> getGenres() {
    return genres;
  }

  public void setGenres(Set<GenreDTO> genres) {
    this.genres = genres;
  }

  @Override
  public String toString() {
    return "MovieDTO{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", lang='" + lang + '\'' +
      ", movieLength=" + movieLength +
      ", description='" + description + '\'' +
      ", rating=" + rating +
      ", likes=" + likes +
      ", director='" + director + '\'' +
      ", imagePath='" + imagePath + '\'' +
      ", releaseDate='" + releaseDate + '\'' +
      ", genres=" + genres +
      '}';
  }
}
