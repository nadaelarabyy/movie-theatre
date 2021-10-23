package com.movietheatre.backend.service;

import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.entities.Rate;
import com.movietheatre.backend.entities.RateId;
import com.movietheatre.backend.entities.User;
import com.movietheatre.backend.reposiory.RatingRespository;
import com.movietheatre.backend.reposiory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final MovieService movieService;
  private final RatingRespository ratingRespository;
  @Autowired
  public UserService(UserRepository userRepository,
                     MovieService movieService,
                     RatingRespository ratingRespository) {
    this.userRepository = userRepository;
    this.movieService = movieService;
    this.ratingRespository = ratingRespository;
  }
  public List<User> getUsers(){
    return userRepository.findAll();
  }
  public User getUserById(Long userId){
    return userRepository.findById(userId).orElse(null);
  }
  public Rate rateMovie(Long userId, Long movieId,double rating){
    User user = userRepository.getById(userId);
    Movie movie= movieService.getMovieById(movieId);
    RateId rateId = new RateId(userId,movieId);
    Rate rate = new Rate();
    rate.setRateId(rateId);
    rate.setMovie(movie);
    rate.setUser(user);
    rate.setRate(rating);
    user.getRates().add(rate);
    movie.getRates().add(rate);
    return ratingRespository.save(rate);

  }
  public Movie flagMovie(Long movieId,Long userId){
    Movie movie = movieService.getMovieById(movieId);
    User user = getUserById(userId);
    if(!user.getInappropriateMovies().contains(movie)){
      user.getInappropriateMovies().add(movie);
      userRepository.save(user);
    }
    boolean entered = false;
    if(!movie.getUsers().contains(user)) {
      int val = movie.getInappropriate();
      movie.setInappropriate(val + 1);
      movie.getUsers().add(user);
      entered = true;
    }
    return entered? movieService.updateMovie(movie):movie;
  }
  public List<Movie> getMoviesInappropriate(Long userId){
    User user = getUserById(userId);
    if(user.getRole().equals("admin")){
      return movieService.getInappropriateMovies();
    }
    return new ArrayList<>();
  }
  public Movie showHideMovie(Long userId, Long movieId){
    User user = getUserById(userId);
    if(user.getRole().equals("admin")){
      Movie movie = movieService.getMovieById(movieId);
      boolean shown = movie.isShown();
      movie.setShown(!shown);
      return movieService.updateMovie(movie);
    }
    return null;
  }

}
