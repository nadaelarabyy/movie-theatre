package com.movietheatre.backend.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Movie {
  @Id
  private Long movieId;
  private String title;
  private String language;
  private int movieLength;
  @Column(length = 65555)
  private String description;
  private double rating;
  private int likes;
  private String director;
  private String imagePath;
  private Date releaseDate;
  private String source;
  @ManyToMany(mappedBy = "movies", fetch=FetchType.EAGER)
  private Set<Genre> genres;
  @ManyToMany(mappedBy = "movies",fetch = FetchType.LAZY)
  private Set<ProductionCompany> productionCompanyList;
  @ManyToMany(mappedBy = "movies",fetch = FetchType.LAZY)
  private Set<Cast> cast;
  @OneToMany(mappedBy = "movie",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  private Set<Review> reviews;
  private int inappropriate;
  private boolean shown;
  @OneToMany(mappedBy = "movie")
  private Set<Rate> rates = new HashSet<>();
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "movies_users_inappropriate",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "movie_id")
  )
  private Set<User> users;

  public Movie() {
  }

  public Movie(String title, String language, int movieLength, String description, double rating,
               int likes, String director, String imagePath, Set<Genre> genres,
               Set<ProductionCompany> productionCompanyList, Set<Cast> cast, Set<Review> reviews,
               int inappropriate, boolean shown,Set<User> users,Date releaseDate,String source) {
    this.title = title;
    this.language = language;
    this.movieLength = movieLength;
    this.description = description;
    this.rating = rating;
    this.likes = likes;
    this.director = director;
    this.imagePath = imagePath;
    this.genres = genres;
    this.productionCompanyList = productionCompanyList;
    this.cast = cast;
    this.reviews = reviews;
    this.inappropriate = inappropriate;
    this.shown = shown;
    this.users = users;
    this.releaseDate = releaseDate;
    this.source = source;
  }

  public Movie(Long id, String title, String language, int movieLength, String description,
               double rating, int likes, String director, String imagePath, Set<Genre> genres,
               Set<ProductionCompany> productionCompanyList, Set<Cast> cast, Set<Review> reviews,
               int inappropriate, boolean shown,Set<User> users,Date releaseDate,String source) {
    this.movieId = id;
    this.title = title;
    this.language = language;
    this.movieLength = movieLength;
    this.description = description;
    this.rating = rating;
    this.likes = likes;
    this.director = director;
    this.imagePath = imagePath;
    this.genres = genres;
    this.productionCompanyList = productionCompanyList;
    this.cast = cast;
    this.reviews = reviews;
    this.inappropriate = inappropriate;
    this.shown = shown;
    this.users = users;
    this.releaseDate = releaseDate;
    this.source = source;
  }

  public Long getId() {
    return movieId;
  }

  public void setId(Long id) {
    this.movieId = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
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
  @JsonManagedReference
  public Set<Genre> getGenres() {
    return genres;
  }

  public void setGenres(Set<Genre> genres) {
    this.genres = genres;
  }
  @JsonManagedReference
  public Set<ProductionCompany> getProductionCompanyList() {
    return productionCompanyList;
  }

  public void setProductionCompanyList(Set<ProductionCompany> productionCompanyList) {
    this.productionCompanyList = productionCompanyList;
  }
  @JsonManagedReference
  public Set<Cast> getCast() {
    return cast;
  }

  public void setCast(Set<Cast> cast) {
    this.cast = cast;
  }
  @JsonManagedReference
  public Set<Review> getReviews() {
    return reviews;
  }

  public void setReviews(Set<Review> reviews) {
    this.reviews = reviews;
  }

  public int getInappropriate() {
    return inappropriate;
  }

  public void setInappropriate(int inappropriate) {
    this.inappropriate = inappropriate;
  }

  public boolean isShown() {
    return shown;
  }

  public void setShown(boolean shown) {
    this.shown = shown;
  }
  @JsonBackReference
  public Set<Rate> getRates() {
    return rates;
  }

  public void setRates(Set<Rate> rates) {
    this.rates = rates;
  }
  @JsonManagedReference
  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  @Override
  public String toString() {
    return "Movie{" +
      "id=" + movieId +
      ", title='" + title + '\'' +
      ", language='" + language + '\'' +
      ", movieLength=" + movieLength +
      ", description='" + description + '\'' +
      ", rating=" + rating +
      ", likes=" + likes +
      ", director='" + director + '\'' +
      ", imagePath='" + imagePath + '\'' +
      ", genres=" + genres +
      ", productionCompanyList=" + productionCompanyList +
      ", cast=" + cast +
      ", reviews=" + reviews +
      ", inappropriate=" + inappropriate +
      ", shown=" + shown +
      '}';
  }
}
