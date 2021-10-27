package com.movietheatre.backend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movietheatre.backend.dto.FlagDTO;
import com.movietheatre.backend.dto.MovieDTO;
import com.movietheatre.backend.dto.MovieEditDTO;
import com.movietheatre.backend.dto.RatingDTO;
import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.entities.Rate;
import com.movietheatre.backend.entities.User;
import com.movietheatre.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
    public ResponseEntity<List<Movie>> getFlaggedMovies(){
        return new ResponseEntity<>(
          userService.getMoviesInappropriate(),
          HttpStatus.OK
        );
      }
      @PutMapping(value = "/show", consumes = "application/json",
      produces = "application/json")
    public ResponseEntity<Movie> showHideMovie(@RequestBody FlagDTO dto){
    return new ResponseEntity<>(
      userService.showHideMovie(dto.getMovieId()),
      HttpStatus.OK
    );
      }
      @GetMapping("/token/refresh")
      public void refreshToken(HttpServletRequest request,
                                            HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
          try {
            String refresh_token = authorizationHeader.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refresh_token);
            String email = decodedJWT.getSubject();
            User user = userService.getUserByEmail(email);
            List<String> roles = new ArrayList<>();
            roles.add(user.getRole());
            String access_token = JWT.create()
              .withSubject(user.getEmail())
              .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
              .withIssuer(request.getRequestURL().toString())
              .withClaim("roles",roles)
              .sign(algorithm);
            Map<String,String> out = new HashMap<>();
            out.put("access_token",access_token);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),out);
          }
          catch (Exception ex){
            response.setHeader("error",ex.getMessage());
            response.setStatus(FORBIDDEN.value());
//            response.sendError(FORBIDDEN.value());
            Map<String,String> error = new HashMap<>();
            error.put("error_message",ex.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),error);
          }
        }
        else{
          throw new RuntimeException("Refresh token is missing!!");
        }

      }

      @PostMapping(value = "/addmovie", produces = "application/json",consumes = "application/json")
      public ResponseEntity<Movie> addMovieByAdmin(@RequestBody MovieDTO movieDTO) throws ParseException {
        System.out.println("==>"+movieDTO);
        Movie movie = userService.addNewMovie(movieDTO);
        return new ResponseEntity<>(movie,HttpStatus.OK);
      }
      @PutMapping(value = "/editmovie",produces = "application/json",consumes = "application/json")
      public ResponseEntity<Movie> editMovieByAdmin(@RequestBody MovieEditDTO movieEditDTO
          ,@RequestParam(value = "id") Long id) throws ParseException {
          Movie movie = userService.editMovie(id,movieEditDTO);
          return new ResponseEntity<>(movie,HttpStatus.OK);
      }


    @GetMapping("/logout")
  public String logout(HttpServletRequest request,HttpServletResponse response) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (auth != null) {
        new SecurityContextLogoutHandler().logout(request, response, auth);
      }

      return "redirect:/login?logout";
    }
}
