package com.movietheatre.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class User {
  @Id
  @SequenceGenerator(
    name = "user_sequence",
    sequenceName = "user_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "user_sequence"
  )
  private Long userId;
  private String email;
  private String password;
  private String role;
  @OneToMany(mappedBy = "user")
  private Set<Rate> rates = new HashSet<>();
  @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
  private Set<Movie> inappropriateMovies;
  public User() {
  }

  public User(String email, String password, String role, Set<Movie> inappropriateMovies) {
    this.email = email;
    this.password = password;
    this.role = role;
    this.inappropriateMovies = inappropriateMovies;
  }

  public User(Long id, String email, String password, String role, Set<Movie> inappropriateMovies) {
    this.userId = id;
    this.email = email;
    this.password = password;
    this.role = role;
    this.inappropriateMovies = inappropriateMovies;
  }

  public Long getId() {
    return userId;
  }

  public void setId(Long id) {
    this.userId = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
  @JsonBackReference
  public Set<Rate> getRates() {
    return rates;
  }

  public void setRates(Set<Rate> rates) {
    this.rates = rates;
  }
  @JsonBackReference
  public Set<Movie> getInappropriateMovies() {
    return inappropriateMovies;
  }

  public void setInappropriateMovies(Set<Movie> inappropriateMovies) {
    this.inappropriateMovies = inappropriateMovies;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + userId +
      ", email='" + email + '\'' +
      ", password='" + password + '\'' +
      ", role='" + role + '\'' +
      '}';
  }
}
