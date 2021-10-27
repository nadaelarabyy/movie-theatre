package com.movietheatre.backend.controller;

import com.movietheatre.backend.TestConfig;
import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.entities.Rate;
import com.movietheatre.backend.entities.RateId;
import com.movietheatre.backend.entities.User;
import com.movietheatre.backend.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@Import({TestConfig.class})
class UserControllerTest {
  @MockBean
  private UserService userService;
  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  private MockMvc mockMvc;



  @Test
  void getAllUsers() throws Exception{
    List<User> users = getMockUsers();
    when(userService.getUsers()).thenReturn(users);
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
        .with(SecurityMockMvcRequestPostProcessors.user("nadaelarabyy@yahoo.com")
          .roles("admin").password("nada@123")))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(jsonPath("$.size()", Matchers.is(2)))
      .andExpect(jsonPath("$[0].email").value("mock1@email.com"))
      .andExpect(jsonPath("$[1].email").value("mock2@email.com"));
  }


  @Test
//  viewer
  void rateMovie_as_viewer() throws Exception{
    Rate rate = getMockRate();
    when(userService.rateMovie(2L,19404L,2.2)).thenReturn(rate);
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("viewer"));
    mockMvc.perform(MockMvcRequestBuilders.post("/api/users/rate")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"movieId\" : 19404, \"userId\" : 2,\"rating\":2.2 }")
        .accept(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.user("salmanabil@gmail.com")
          .roles("viewer").password("salma@123").authorities(authorities))
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(jsonPath("$.rate").exists())
      .andExpect(jsonPath("$.rate").value(2.2))
      .andExpect(jsonPath("$.rateId.movieId").exists())
      .andExpect(jsonPath("$.rateId.movieId").value(19404))
      .andExpect(jsonPath("$.rateId.userId").exists())
      .andExpect(jsonPath("$.rateId.userId").value(2));
  }
  @Test
//  viewer
  void rateMovie_as_admin() throws Exception{
    Rate rate = getMockRate();
    when(userService.rateMovie(1L,19404L,2.2)).thenReturn(rate);
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("admin"));
    mockMvc.perform(MockMvcRequestBuilders.post("/api/users/rate")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"movieId\" : 19404, \"userId\" : 1,\"rating\":2.2 }")
        .accept(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.user("nadaelarabyy@yahoo.com")
          .roles("admin").password("nada@123").authorities(authorities))
      )
      .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
//  viewer
  void flagMovie_as_viewer() throws Exception{
    Movie movie = getMovie();
    when(userService.flagMovie(19404L,2L)).thenReturn(movie);
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("viewer"));
    mockMvc.perform(MockMvcRequestBuilders.put("/api/users/flag")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"movieId\" : 19404, \"userId\" : 2}")
        .accept(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.user("salmanabil@gmail.com")
          .roles("viewer").password("salma@123").authorities(authorities))
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(jsonPath("$.title").value("mock title"))
      .andExpect(jsonPath("$.description").value("mock descritpion"))
      .andExpect(jsonPath("$.director").value("mock director"));

  }

  @Test
//  viewer
  void flagMovie_as_admin() throws Exception{
    Movie movie = getMovie();
    when(userService.flagMovie(19404L,1L)).thenReturn(movie);
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("admin"));
    mockMvc.perform(MockMvcRequestBuilders.put("/api/users/flag")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"movieId\" : 19404, \"userId\" : 1}")
        .accept(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.user("nadaelarabyy@yahoo.com")
          .roles("admin").password("nada@123").authorities(authorities))
      )
      .andExpect(MockMvcResultMatchers.status().isForbidden());

  }

  @Test
  void getFlaggedMovies_as_an_admin() throws Exception {
    Movie movie = getMovie();
    when(userService.getMoviesInappropriate()).thenReturn(List.of(movie));
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("admin"));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/flagged")
        .with(SecurityMockMvcRequestPostProcessors.user("nadaelarabyy@yahoo.com")
          .roles("admin").password("nada@123").authorities(authorities)))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(jsonPath("$.size()", Matchers.is(1)))
      .andExpect(jsonPath("$[0].title").value("mock title"))
      .andExpect(jsonPath("$[0].description").value("mock descritpion"))
      .andExpect(jsonPath("$[0].director").value("mock director"));

  }
  @Test
//  admin
  void getFlaggedMovies_as_a_viewer() throws Exception {
    Movie movie = getMovie();
    when(userService.getMoviesInappropriate()).thenReturn(List.of(movie));
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("viewer"));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/flagged")
        .with(SecurityMockMvcRequestPostProcessors.user("salmanabil@gmail.com")
          .roles("viewer").password("salma@123").authorities(authorities)))
      .andExpect(MockMvcResultMatchers.status().isForbidden());

  }

  @Test
//  admin
  void showHideMovie_as_admin() throws Exception{
    Movie movie = getMovie();
    when(userService.showHideMovie(19404L)).thenReturn(movie);
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("admin"));
    mockMvc.perform(MockMvcRequestBuilders.put("/api/users/show")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"movieId\" : 19404, \"userId\" : 1}")
        .accept(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.user("nadaelarabyy@yahoo.com")
          .roles("admin").password("nada@123").authorities(authorities))
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(jsonPath("$.title").value("mock title"))
      .andExpect(jsonPath("$.description").value("mock descritpion"))
      .andExpect(jsonPath("$.director").value("mock director"));

  }
  @Test
//  admin
  void showHideMovie_as_viewer() throws Exception{
    Movie movie = getMovie();
    when(userService.showHideMovie(19404L)).thenReturn(movie);
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("viewer"));
    mockMvc.perform(MockMvcRequestBuilders.put("/api/users/show")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"movieId\" : 19404, \"userId\" : 2}")
        .accept(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.user("salmanabil@gmail.com")
          .roles("viewer").password("salma@123").authorities(authorities))
      )
      .andExpect(MockMvcResultMatchers.status().isForbidden());

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
      0, false,new HashSet<>());
  }
  public Rate getMockRate(){
    RateId rateId = new RateId(2L,19404L);
    Rate rate = new Rate();
    rate.setRateId(rateId);
    rate.setMovie(null);
    rate.setUser(null);
    rate.setRate(2.2);
    return rate;
  }
}
