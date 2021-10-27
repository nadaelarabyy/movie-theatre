package com.movietheatre.backend.reposiory;

import com.movietheatre.backend.TestConfig;
import com.movietheatre.backend.entities.Cast;
import com.movietheatre.backend.entities.Review;
import com.movietheatre.backend.entities.StageName;
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
class StageNameRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private StageNameRepository stageNameRepository;
  //  save a stage name
  @Test
  public void save_a_stage_name(){
    StageName stageName = getStageName();
    StageName stageNameReturned = stageNameRepository.save(stageName);
    assertEquals(stageNameReturned.getId(),stageName.getId());
  }
  @Test
  //  delete all stage names
  public void delete_all_stage_names(){
    StageName stageName = getStageName();
    entityManager.persist(stageName);
    entityManager.flush();
    stageNameRepository.deleteAll();
    assertEquals(stageNameRepository.findAll().size(),0);

  }
  //  find all stage names
  @Test
  public void find_all_stage_names(){
    StageName stageName = getStageName();
    entityManager.persist(stageName);
    List<StageName> stageNameList = stageNameRepository.findAll();
    assertEquals(1, stageNameList.size());
    assertTrue(stageNameList.contains(stageName));

  }

  //  find stage name by id
  @Test
  public void find_stage_name_by_id(){
    StageName stageName = getStageName();
    entityManager.persist(stageName);
    StageName stageNameReturned = stageNameRepository.findById(stageName.getId()).get();
    assertEquals(stageNameReturned.getName(), stageName.getName());

  }

  public StageName getStageName(){
    Cast cast = new Cast(-1L,"image Path","mock artist",
      new HashSet<>(),new HashSet<>());
    return new StageName("Mock stage name", cast);
  }

}
