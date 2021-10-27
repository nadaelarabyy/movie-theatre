package com.movietheatre.backend.reposiory;

import com.movietheatre.backend.TestConfig;
import com.movietheatre.backend.entities.Cast;
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
class CastRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private CastRepository castRepository;

//  find no cast if repo is empty
  @Test
  public void find_no_cast_if_repo_is_empty(){
    List<Cast> cast = castRepository.findAll();
    assertEquals(cast.size(),0);
  }
//  save a cast member
  @Test
  public void save_a_cast_member(){
    Cast cast = getCast();
    Cast castRepoReturned = castRepository.save(cast);
    assertEquals(castRepoReturned.getId(),cast.getId());
  }
//  delete all cast members
  public void delete_all_cast_members(){
    Cast cast1 = getCast();
    cast1.setId(-2L);
    Cast cast2 = getCast();
    cast2.setId(-3L);
    entityManager.persist(cast1);
    entityManager.persist(cast2);
    castRepository.deleteAll();
    assertEquals(castRepository.findAll().size(),0);

  }
//  find all cast members
  @Test
  public void find_all_cast_members(){
    Cast cast1 = getCast();
    cast1.setId(-2L);
    entityManager.persist(cast1);
    Cast cast2 = getCast();
    cast2.setId(-3L);
    entityManager.persist(cast2);
    Cast cast3 = getCast();
    cast3.setId(-4L);
    entityManager.persist(cast3);
    List<Cast> castSet = castRepository.findAll();
    assertEquals(3, castSet.size());
    assertTrue(castSet.contains(cast1));
    assertTrue(castSet.contains(cast2));
    assertTrue(castSet.contains(cast3));
  }

//  find cast member by id
  @Test
  public void find_cast_member_by_id(){
    Cast cast1 = getCast();
    cast1.setId(-200L);
    cast1.setName("Name1");
    entityManager.persist(cast1);
    Cast cast2 = getCast();
    cast2.setId(-300L);
    cast2.setName("Name2");
    entityManager.persist(cast2);
    Cast castReturned = castRepository.findById(cast2.getId()).get();
    assertEquals(castReturned.getName(), cast2.getName());

  }
  public Cast getCast(){
    return new Cast(-1L,"image Path","mock artist",
      new HashSet<>(),new HashSet<>());

  }




}
