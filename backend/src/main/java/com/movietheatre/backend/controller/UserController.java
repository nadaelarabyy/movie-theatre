package com.movietheatre.backend.controller;

import com.movietheatre.backend.dto.FlagDTO;
import com.movietheatre.backend.dto.RatingDTO;
import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.entities.Rate;
import com.movietheatre.backend.entities.User;
import com.movietheatre.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {
  private final UserService userService;
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers(){
      List<User> users = userService.getUsers();
      return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @PostMapping(value = "/rate", produces = "application/json",consumes = "application/json")
    public ResponseEntity<Rate> rateMovie(@RequestBody RatingDTO ratingDTO){
    Rate rating = userService.rateMovie(ratingDTO.getUserId(),ratingDTO.getMovieId(),ratingDTO.getRating());
    return new ResponseEntity<>(rating,HttpStatus.OK);
      }
    @PutMapping(value = "/flag", produces = "application/json",consumes = "application/json")
    public ResponseEntity<Movie> flagMovie(@RequestBody FlagDTO flagDTO){
        return new ResponseEntity<>(
          userService.flagMovie(flagDTO.getMovieId(),flagDTO.getUserId()),
          HttpStatus.OK);
      }
      @GetMapping("/flagged")
    public ResponseEntity<List<Movie>> getFlaggedMovies(@RequestParam(value = "id")
                                                 Long userId){
        return new ResponseEntity<>(
          userService.getMoviesInappropriate(userId),
          HttpStatus.OK
        );
      }
      @PutMapping(value = "/show", consumes = "application/json",
      produces = "application/json")
    public ResponseEntity<Movie> showHideMovie(@RequestBody FlagDTO dto){
    return new ResponseEntity<>(userService.showHideMovie(dto.getUserId(),
      dto.getMovieId()),HttpStatus.OK);
      }


}
