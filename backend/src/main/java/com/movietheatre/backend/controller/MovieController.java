package com.movietheatre.backend.controller;
import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/movies")
public class MovieController {
  private final MovieService movieService;
  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }
  @GetMapping("")
  public ResponseEntity<List<Movie>> getAllMovies(){
    List<Movie> topMovies = movieService.getAllMovies();
    return new ResponseEntity<>(topMovies,HttpStatus.OK);
  }
  @GetMapping("/pages")
 public ResponseEntity<List<Movie>> getMoviesPaginated(@RequestParam(value = "page") int page,
                                                       @RequestParam(value = "pagesize") int size){
    List<Movie> movies = movieService.getMoviesPaginated(page,size);
    return new ResponseEntity<>(movies,HttpStatus.OK);
 }
  @GetMapping("/movie")
  public ResponseEntity<Movie> getMovieById(@RequestParam(value = "id") Long id){

    Movie movie = movieService.getMovieDetailsById(id);
    if(movie==null){
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    else{

      return new ResponseEntity<>(movie,HttpStatus.OK);

    }

  }
  @GetMapping("/recommend")
  public ResponseEntity<List<Movie>> getMovieGenre(
    @RequestParam(value = "id") Long id){
      return new ResponseEntity(movieService.getMovieGenres(id),
        HttpStatus.OK);
  }
}
