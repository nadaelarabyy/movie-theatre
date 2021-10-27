package com.movietheatre.backend.controller;

import com.movietheatre.backend.TestConfig;
import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.entities.Rate;
import com.movietheatre.backend.entities.RateId;
import com.movietheatre.backend.service.MovieService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(MovieController.class)
@Import({TestConfig.class})
class MovieControllerTest {
  @MockBean
  private MovieService movieService;
  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  private MockMvc mockMvc;

  @Test
  void getAllMovies() throws Exception {
    Movie movie = getMovie();
    when(movieService.getAllMovies()).thenReturn(List.of(movie));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/movies")
        .with(SecurityMockMvcRequestPostProcessors.user("salmanabil@gmail.com").roles("viewer")
          .password("salma@123")))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("mock title"));
  }

  @Test
  void getMoviesPaginated() throws Exception{
    Movie movie = getMovie();
    when(movieService.getMoviesPaginated(1,1)).thenReturn(List.of(movie));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/pages?page=1&pagesize=1")
        .with(SecurityMockMvcRequestPostProcessors.user("salmanabil@gmail.com").roles("viewer")
          .password("salma@123")))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("mock title"));
  }

  @Test
  void getMovieById() throws Exception{
    Movie movie = getMovie();
    when(movieService.getMovieDetailsById(19404L)).thenReturn(movie);
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/movie?id=19404")
        .with(SecurityMockMvcRequestPostProcessors.user("salmanabil@gmail.com").roles("viewer")
          .password("salma@123")))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("mock title"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.director").value("mock director"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("mock descritpion"));
  }

  @Test
  void getMovieById_null() throws Exception{
    Movie movie = getMovie();
    when(movieService.getMovieDetailsById(238L)).thenReturn(movie);
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/movie?id=19404")
        .with(SecurityMockMvcRequestPostProcessors.user("salmanabil@gmail.com").roles("viewer")
          .password("salma@123")))
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void getMovieGenre() throws Exception{
    Movie movie = getMovie();
    when(movieService.getMovieGenres(19404L)).thenReturn(List.of(movie));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/recommend?id=19404")
        .with(SecurityMockMvcRequestPostProcessors.user("salmanabil@gmail.com").roles("viewer")
          .password("salma@123")))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("mock title"));
  }

  public Movie getMovie(){

    return new Movie(19404L, "mock title", "en", 90, "mock descritpion",
      2.2, 15, "mock director", "mock image path", new HashSet<>(),
      new HashSet<>(), new HashSet<>(), new HashSet<>(),
      0, false,new HashSet<>());
  }
}
