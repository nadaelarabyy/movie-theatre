package com.movietheatre.backend.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
public class RateId implements Serializable {
  private Long userId;
  private Long movieId;

  public RateId() {
  }

  public RateId(Long userId, Long movieId) {
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
    return "RateId{" +
      "userId=" + userId +
      ", movieId=" + movieId +
      '}';
  }
}
