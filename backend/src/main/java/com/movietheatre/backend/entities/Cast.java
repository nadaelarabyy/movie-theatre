package com.movietheatre.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Cast {
  @Id
  private Long id;
  private String imagePath;
  private String name;
  @OneToMany(mappedBy = "cast",cascade = CascadeType.ALL)
  private Set<StageName> stageNameList;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "movies_cast",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "cast_id")
  )
  private Set<Movie> movies;

  public Cast() {
  }

  public Cast(Long id, String imagePath, String name,
              Set<StageName> stageNameList, Set<Movie> movies) {
    this.id = id;
    this.imagePath = imagePath;
    this.name = name;
    this.stageNameList = stageNameList;
    this.movies = movies;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  @JsonManagedReference
  public Set<StageName> getStageNameList() {
    return stageNameList;
  }

  public void setStageNameList(Set<StageName> stageNameList) {
    this.stageNameList = stageNameList;
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
    return "Cast{" +
      "id=" + id +
      ", imagePath='" + imagePath + '\'' +
      ", name='" + name + '\'' +
      ", stageNameList=" + stageNameList +
      ", movies=" + movies +
      '}';
  }
}
