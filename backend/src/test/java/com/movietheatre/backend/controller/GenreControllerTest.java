package com.movietheatre.backend.controller;

import com.movietheatre.backend.TestConfig;
import com.movietheatre.backend.entities.Genre;
import com.movietheatre.backend.service.GenreService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(GenreController.class)
@Import({TestConfig.class})
class GenreControllerTest {
  @MockBean
  private GenreService genreService;
  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  private MockMvc mockMvc;

  @Test
  void getGenres() throws Exception {
    List<Genre> genres = getMockGenres();
    when(genreService.getGenres()).thenReturn(genres);
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/genres")
        .with(SecurityMockMvcRequestPostProcessors.user("salmanabil@gmail.com").roles("viewer")
          .password("salma@123")))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].genreName").value("mock genre1"))
      .andExpect(MockMvcResultMatchers.jsonPath("$[1].genreName").value("mock genre2"));
  }

  @Test
  void getGenreById() throws Exception {
    Genre genre = getMockGenres().get(0);
    when(genreService.getGenreById(1L)).thenReturn(genre);
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/genres/genre?id=1")
        .with(SecurityMockMvcRequestPostProcessors.user("salmanabil@gmail.com").roles("viewer")
          .password("salma@123")))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.genreName").value("mock genre1"));

  }
  public List<Genre> getMockGenres(){
    List<Genre> genres = new ArrayList<>();
    Genre genre1 = new Genre(1L,"mock genre1",new HashSet<>());
    Genre genre2 = new Genre(2L,"mock genre2",new HashSet<>());
    genres.add(genre1);
    genres.add(genre2);
    return genres;
  }
}
