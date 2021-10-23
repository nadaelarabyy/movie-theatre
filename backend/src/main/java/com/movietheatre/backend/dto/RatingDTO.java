package com.movietheatre.backend.dto;

import java.io.Serializable;

public class RatingDTO implements Serializable {
  private Long movieId;
  private Long userId;
  private double rating;

  public RatingDTO(Long movieId, Long userId, double rating) {
    this.movieId = movieId;
    this.userId = userId;
    this.rating = rating;
  }

  public Long getMovieId() {
    return movieId;
  }

  public void setMovieId(Long movieId) {
    this.movieId = movieId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  @Override
  public String toString() {
    return "RatingDTO{" +
      "movieId=" + movieId +
      ", userId=" + userId +
      ", rating=" + rating +
      '}';
  }
}
