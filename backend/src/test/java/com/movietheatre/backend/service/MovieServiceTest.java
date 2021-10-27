package com.movietheatre.backend.service;

import com.movietheatre.backend.entities.Genre;
import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.reposiory.MovieReposiory;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MovieServiceTest {
  @Mock
  private MovieReposiory movieReposiory;
  @Mock
  private CastService castService;
  @Mock
  private ProductionCompanyService productionCompanyService;
  @Mock
  private ReviewService reviewService;
  @Spy
  @InjectMocks
  private MovieService movieService;
  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getAllMovies() {
    Movie movie1 = getMovie();
    Movie movie2 = getMovie();
    movie2.setId(238L);
    List<Movie> movies = new ArrayList<>();
    movies.add(movie1);
    movies.add(movie2);
    when(movieReposiory.findAll()).thenReturn(movies);
    List<Movie> result = movieService.getAllMovies();
    assertEquals(result.size(),2);
  }

  @Test
  public void getMovieById() {
    Movie movie = getMovie();
    when(movieReposiory.findById(19404L)).thenReturn(Optional.of(movie));
    Movie result = movieService.getMovieById(19404L);
    assertEquals(result.getTitle(),movie.getTitle());
    assertEquals(result,movie);
  }

  @Test
  public void getMovieDetailsById() {
    Movie movie = getMovie();
    when(movieReposiory.findById(19404L))
      .thenReturn(Optional.of(movie));
    movieService.getMovieDetailsById(19404L);
    verify(movieReposiory,times(1)).findById(19404L);
    verify(castService,times(1)).addCastCrewToMovie(movie);
    verify(reviewService,times(1)).addReviewsToMovie(movie);

  }

  @Test
  public void getMoviesPaginated() {
    Movie movie1 = getMovie();
    Movie movie2 = getMovie();
    movie2.setId(238L);
    List<Movie> movies = new ArrayList<>();
    movies.add(movie1);
    movies.add(movie2);
    Pageable paging = PageRequest.of(1, 2);
    Page<Movie> moviePages = new PageImpl(movies);
    when(movieReposiory.findAll(paging)).thenReturn(moviePages);
    List<Movie> movieResult = movieService
      .getMoviesPaginated(1,2);
    assertEquals(movieResult.size(),2);
  }

  @Test
  public void getMovieGenres() {
    Movie movie1 = getMovie();
    Movie movie2 = getMovie();
    movie2.setId(238L);
    List<Movie> movies = new ArrayList<>();
    movies.add(movie1);
    movies.add(movie2);
    when(movieReposiory.findById(19404L)).thenReturn(Optional.of(movie1));
    List<Movie> res = movieService.getMovieGenres(19404L);
    assertEquals(0,res.size());

  }

  @Test
  public void updateMovie() {
    Movie movie = getMovie();
    when(movieReposiory.saveAndFlush(movie)).thenReturn(movie);
    Movie result = movieService.updateMovie(movie);
    assertEquals(movie,result);
  }

  @Test
  public void getInappropriateMovies() {
    Movie movie1 = getMovie();
    movie1.setInappropriate(11);
    Movie movie2 = getMovie();
    movie2.setId(238L);
    movie2.setInappropriate(12);
    List<Movie> movies = new ArrayList<>();
    movies.add(movie1);
    movies.add(movie2);
    when(movieReposiory.findAll()).thenReturn(movies);
    List<Movie> result = movieService.getInappropriateMovies();
    assertEquals(2,result.size());
    assertTrue(result.contains(movie1));
    assertTrue(result.contains(movie2));
  }
  public Movie getMovie(){

    return new Movie(19404L, "mock title", "en", 90, "mock descritpion",
      2.2, 15, "mock director", "mock image path", new HashSet<>(),
      new HashSet<>(), new HashSet<>(), new HashSet<>(),
      0, false,new HashSet<>());
  }
}
