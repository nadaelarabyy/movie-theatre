package com.movietheatre.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table
public class Review {
  @Id
  @SequenceGenerator(
    name = "review_sequence",
    sequenceName = "review_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "review_sequence"
  )
  private Long id;
  private String authorName;
  private String issueDate;
  @Column(length = 70000)
  private String content;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "movie_id")
  private Movie movie;

  public Review() {
  }

  public Review(String authorName, String issueDate, String content,
                Movie movie) {
    this.authorName = authorName;
    this.issueDate = issueDate;
    this.content = content;
    this.movie = movie;
  }

  public Review(Long id, String authorName, String issueDate, String content, Movie movie) {
    this.id = id;
    this.authorName = authorName;
    this.issueDate = issueDate;
    this.content = content;
    this.movie = movie;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public String getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(String issueDate) {
    this.issueDate = issueDate;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
  @JsonBackReference
  public Movie getMovie() {
    return movie;
  }

  public void setMovie(Movie movie) {
    this.movie = movie;
  }

  @Override
  public String toString() {
    return "Review{" +
      "id=" + id +
      ", authorName='" + authorName + '\'' +
      ", issueDate=" + issueDate +
      ", content='" + content + '\'' +
      ", movie=" + movie +
      '}';
  }
}
