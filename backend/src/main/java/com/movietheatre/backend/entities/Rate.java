package com.movietheatre.backend.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class Rate{
  @EmbeddedId
  private RateId rateId = new RateId();
  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private User user;
  @ManyToOne
  @MapsId("movieId")
  @JoinColumn(name = "movie_id")
  private Movie movie;
  private double rate;

  public Rate() {
  }

  public RateId getRateId() {
    return rateId;
  }

  public void setRateId(RateId rateId) {
    this.rateId = rateId;
  }
  @JsonManagedReference
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
  @JsonManagedReference
  public Movie getMovie() {
    return movie;
  }

  public void setMovie(Movie movie) {
    this.movie = movie;
  }

  public double getRate() {
    return rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }

  @Override
  public String toString() {
    return "Rate{" +
      "rateId=" + rateId +
      ", user=" + user +
      ", movie=" + movie +
      ", rate=" + rate +
      '}';
  }
}
