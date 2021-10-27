package com.movietheatre.backend.reposiory;

import com.movietheatre.backend.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class MovieReposioryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private MovieReposiory movieReposiory;

  //  find no movie if repo is empty
  @Test
  public void find_no_movie_if_repo_is_empty(){
    List<Movie> movies = movieReposiory.findAll();
    assertEquals(movies.size(),0);
  }

  //  save a movie
  @Test
  public void save_a_movie(){
    Movie movie = getMovie();
    Movie movieRepoReturned = movieReposiory.save(movie);
    assertEquals(movieRepoReturned.getId(),movie.getId());
  }
  @Test
  //  delete all movies
  public void delete_all_movies(){
    Movie movie1 = getMovie();
    movie1.setId(-2L);
    Movie movie2 = getMovie();
    movie2.setId(-3L);
    entityManager.persist(movie1);
    entityManager.persist(movie2);
    movieReposiory.deleteAll();
    assertEquals(movieReposiory.findAll().size(),0);

  }
  //  find all movies
  @Test
  public void find_all_movies(){
    Movie movie1 = getMovie();
    movie1.setId(-4L);
    entityManager.persist(movie1);
    Movie movie2 = getMovie();
    movie2.setId(-5L);
    entityManager.persist(movie2);
    Movie movie3 = getMovie();
    movie3.setId(-6L);
    entityManager.persist(movie3);
    List<Movie> movieSet = movieReposiory.findAll();
    assertEquals(3, movieSet.size());
    assertTrue(movieSet.contains(movie1));
    assertTrue(movieSet.contains(movie2));
    assertTrue(movieSet.contains(movie3));
  }

  //  find movie by id
  @Test
  public void find_movie_by_id(){
    Movie movie1 = getMovie();
    movie1.setId(-200L);
    movie1.setTitle("Mock title1");
    entityManager.persist(movie1);
    Movie movie2 = getMovie();
    movie2.setId(-300L);
    movie2.setTitle("Mock title 2");
    entityManager.persist(movie2);
    Movie movieReturned = movieReposiory.findById(movie2.getId()).get();
    assertEquals(movieReturned.getTitle(), movie2.getTitle());

  }

  public Movie getMovie(){
    return new Movie(-1L, "mock title", "en", 90, "mock descritpion",
    2.2, 15, "mock director", "mock image path", new HashSet<>(),
      new HashSet<>(), new HashSet<>(), new HashSet<>(),
    0, false,new HashSet<>());
  }

}
