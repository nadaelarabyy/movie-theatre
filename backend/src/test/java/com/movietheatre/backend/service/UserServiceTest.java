package com.movietheatre.backend.service;

import com.movietheatre.backend.dto.GenreDTO;
import com.movietheatre.backend.dto.MovieDTO;
import com.movietheatre.backend.dto.MovieEditDTO;
import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.entities.Rate;
import com.movietheatre.backend.entities.RateId;
import com.movietheatre.backend.entities.User;
import com.movietheatre.backend.reposiory.RatingRespository;
import com.movietheatre.backend.reposiory.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private MovieService movieService;
  @Mock
  private RatingRespository ratingRespository;
  @Spy
  @InjectMocks
  private UserService userService;
  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
  }
  @Test
  public void getUsers() {
    List<User> users = getMockUsers();
    when(userRepository.findAll()).thenReturn(users);
    List usersRes = userService.getUsers();
    assertEquals(users.size(),usersRes.size());
  }

  @Test
  public void getUserByEmail() {
    List<User> users = getMockUsers();
    when(userRepository.findAll()).thenReturn(users);
    User userReturned = userService.getUserByEmail("mock1@email.com");
    verify(userRepository,atLeastOnce()).findAll();
    assertEquals(userReturned.getEmail(),"mock1@email.com");
  }

  @Test
  public void getUserById() {
    User user = getMockUsers().get(0);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    User userReturned = userService.getUserById(1L);
    assertEquals(user.getEmail(),userReturned.getEmail());
  }

  @Test
//  issue here ====>
  public void rateMovie() {
    User user = getMockUsers().get(0);
    Movie movie = getMovie();
    when(userRepository.getById(user.getId())).thenReturn(user);
    when(movieService.getMovieById(movie.getId())).thenReturn(movie);
    RateId rateId = new RateId(user.getId(),movie.getId());
    Rate rate = new Rate();
    rate.setRateId(rateId);
    rate.setMovie(movie);
    rate.setUser(user);
    rate.setRate(2.2);
    double movieAvg = movie.getRates().stream().mapToDouble(Rate::getRate)
      .average().orElse(0.0);
    movie.setRating(movieAvg);
    when(ratingRespository.save(rate)).thenReturn(rate);
    Rate rateReturned = userService.rateMovie(user.getId(),movie.getId(),2.2);
    System.out.println(rateReturned);
  }

  @Test
  public void flagMovie() {
    Movie movie=getMovie();
    when(movieService.getMovieById(movie.getId())).thenReturn(movie);
    User user = getMockUsers().get(0);
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    when(movieService.updateMovie(movie)).thenReturn(movie);
    Movie movieReturned = userService.flagMovie(movie.getId(),user.getId());
    assertEquals(movieReturned.getTitle(),movie.getTitle());

  }

  @Test
  public void getMoviesInappropriate() {
    Movie movie1 = getMovie();
    Movie movie2 = getMovie();
    movie2.setId(238L);
    List<Movie> movies = new ArrayList<>();
    movies.add(movie1);
    movies.add(movie2);
    when(movieService.getInappropriateMovies()).thenReturn(movies);
    List<Movie> moviesReturned = userService.getMoviesInappropriate();
    assertEquals(moviesReturned.size(),movies.size());
  }

  @Test
  public void showHideMovie() {
    Movie movie = getMovie();
    when(movieService.getMovieById(movie.getId())).thenReturn(movie);
    when(movieService.updateMovie(movie)).thenReturn(movie);
    movie.setShown(!movie.isShown());
    Movie movieReturned = userService.showHideMovie(movie.getId());
    assertEquals(movieReturned.getTitle(),movie.getTitle());

  }
  @Test
  public void addMovieByAdminTest() throws ParseException {
    Movie movie = getMovie();
    MovieDTO movieDTO = getMovieDTO();
    userService.addNewMovie(movieDTO);

    verify(movieService,times(1)).addMovieByAdmin(any());
   }
  @Test
  public void editMovieByAdminTest() throws ParseException {
    MovieEditDTO movieEditDTO = getMoviEditDTO();
    Movie movie = getMovie();
    when(movieService.editMovie(19404L,movieEditDTO)).thenReturn(movie);
    movieService.editMovie(19404L,movieEditDTO);
    verify(movieService,times(1)).editMovie(19404L,movieEditDTO);
  }
  public MovieEditDTO getMoviEditDTO(){
    return new MovieEditDTO(new ArrayList<GenreDTO>(), "2020-12-27", "ar");
  }
  public MovieDTO getMovieDTO(){
    return new MovieDTO(19404L, "mock title", "en",90, "mock descritpion", 2.2,
      15, "mock director", "mock image path", "2020-12-27",
      new HashSet<>());
  }
  public List<User> getMockUsers(){
    List<User> users=new ArrayList<>();
    User user1 = new User(1L, "mock1@email.com", "mock123", "viewer",
      new HashSet<>());
    user1.setRates(new HashSet<>());
    User user2 = new User(2L, "mock2@email.com", "mock1234", "admin",
      new HashSet<>());
    user2.setRates(new HashSet<>());
    users.add(user1);
    users.add(user2);
    return users;
  }
  public Movie getMovie(){
    return new Movie(19404L, "mock title", "en", 90, "mock descritpion",
      2.2, 15, "mock director", "mock image path", new HashSet<>(),
      new HashSet<>(), new HashSet<>(), new HashSet<>(),
      0, false,new HashSet<>(),new Date(),"tmdb");
  }
}
