package com.movietheatre.backend.service;

import com.movietheatre.backend.entities.Genre;
import com.movietheatre.backend.reposiory.GenreRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class GenreServiceTest {
  @Mock
  private GenreRepository genreRepository;
  @Spy
  @InjectMocks
  private GenreService genreService;
  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
  }


  @Test
  public void getGenres() {
    List<Genre> genres = new ArrayList<>();
    Genre genre = getGenre();
    genres.add(genre);
    when(genreRepository.findAll()).thenReturn(genres);
    List<Genre> result = genreService.getGenres();
    assertEquals(1 , result.size());
    assertTrue(result.contains(genre));


  }

  @Test
  public void getGenreById() {
    Genre genre = getGenre();
    when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
    Genre genre1 = genreService.getGenreById(1L);
    assertEquals(genre1,genre);
  }
  public Genre getGenre(){
    return new Genre(1L,"mock genre name",
      new HashSet<>());
  }
}
