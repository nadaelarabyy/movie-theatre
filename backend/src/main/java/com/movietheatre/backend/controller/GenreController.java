package com.movietheatre.backend.controller;

import com.movietheatre.backend.entities.Genre;
import com.movietheatre.backend.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(path = "api/genres")
public class GenreController {
  private final GenreService genreService;
  @Autowired
  public GenreController(GenreService genreService) {
    this.genreService = genreService;
  }

  @GetMapping("")
  public ResponseEntity<List<Genre>> getGenres() {
    List<Genre> genres = genreService.getGenres();
    return new ResponseEntity<>(genres,HttpStatus.OK);
  }
  @GetMapping("/genre")
  public ResponseEntity<Genre> getGenreById(@RequestParam(value = "id") Long id){
    Genre genre = genreService.getGenreById(id);
    if(genre == null){
      return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    else{
      System.out.println();
      return new ResponseEntity<>(genre, HttpStatus.OK);
    }
  }

}
