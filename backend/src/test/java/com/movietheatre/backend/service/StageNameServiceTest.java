package com.movietheatre.backend.service;

import com.movietheatre.backend.entities.Cast;
import com.movietheatre.backend.entities.StageName;
import com.movietheatre.backend.reposiory.StageNameRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class StageNameServiceTest {
  @Mock
  private StageNameRepository stageNameRepository;
  @Spy
  @InjectMocks
  private StageNameService stageNameService;
  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
  }
  @Test
  void addStageName() {
    StageName stageName = new StageName("mockStage Name",
      new Cast());
    when(stageNameRepository.save(stageName)).thenReturn(stageName);
    StageName result = stageNameService.addStageName(stageName);
    System.out.println(result);
    assertEquals(result.getName(),stageName.getName());

  }
}
