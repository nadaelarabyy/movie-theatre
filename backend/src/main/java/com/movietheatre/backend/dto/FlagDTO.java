package com.movietheatre.backend.dto;

import java.io.Serializable;

public class FlagDTO implements Serializable {
  Long userId;
  Long movieId;

  public FlagDTO(Long userId, Long movieId) {
    this.userId = userId;
    this.movieId = movieId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getMovieId() {
    return movieId;
  }

  public void setMovieId(Long movieId) {
    this.movieId = movieId;
  }

  @Override
  public String toString() {
    return "FlagDTO{" +
      "userId=" + userId +
      ", movieId=" + movieId +
      '}';
  }
}
