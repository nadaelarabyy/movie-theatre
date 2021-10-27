package com.movietheatre.backend.reposiory;
import com.movietheatre.backend.TestConfig;
import com.movietheatre.backend.entities.Genre;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
class GenreRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private GenreRepository genreRepository;
  //  find no genre if repo is empty
  @Test
  public void find_no_genre_if_repo_is_empty(){
    List<Genre> genres = genreRepository.findAll();
    assertEquals(genres.size(),0);
  }
  //  save a genre
  @Test
  public void save_a_genre(){
    Genre genre = getGenre();
    Genre genreRepoReturned = genreRepository.save(genre);
    assertEquals(genreRepoReturned.getId(),genre.getId());
  }
  //  delete all genres
  @Test
  public void delete_all_genres(){
    Genre genre1 = getGenre();
    genre1.setId(-2L);
    Genre genre2 = getGenre();
    genre2.setId(-3L);
    entityManager.persist(genre1);
    entityManager.persist(genre2);
    assertEquals(genreRepository.findAll().size(),2);
    genreRepository.deleteAll();
    assertEquals(genreRepository.findAll().size(),0);

  }
  //  find all genres
  @Test
  public void find_all_genres(){
    Genre genre1 = getGenre();
    genre1.setId(-2L);
    entityManager.persist(genre1);
    Genre genre2 = getGenre();
    genre2.setId(-3L);
    entityManager.persist(genre2);
    Genre genre3 = getGenre();
    genre3.setId(-4L);
    entityManager.persist(genre3);
    List<Genre> genreList = genreRepository.findAll();
    assertTrue(genreList.size() == 3);
    assertTrue(genreList.contains(genre1));
    assertTrue(genreList.contains(genre2));
    assertTrue(genreList.contains(genre3));
  }

  //  find genre by id
  @Test
  public void find_genre_by_id(){
    Genre genre1 = getGenre();
    genre1.setId(-200L);
    genre1.setGenreName("genre 1");
    entityManager.persist(genre1);
    Genre genre2 = getGenre();
    genre2.setId(-300L);
    genre2.setGenreName("genre 2");
    entityManager.persist(genre2);
    Genre genreReturned = genreRepository.findById(genre2.getId()).get();
    assertTrue(genreReturned.getGenreName().equals(genre2.getGenreName()));
  }
  public Genre getGenre(){
    Genre genre = new Genre(-1L,"mock genre",new HashSet<>());
    return genre;
  }


}
