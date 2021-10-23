package com.movietheatre.backend.service;

import com.movietheatre.backend.entities.Genre;
import com.movietheatre.backend.reposiory.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GenreService {
  private final GenreRepository genreRepository;

  @Autowired
  public GenreService(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  public List<Genre> getGenres() {
    return genreRepository.findAll();
  }
  public Genre getGenreById(Long id){
    return genreRepository.findById(id).orElse(null);
  }

}
