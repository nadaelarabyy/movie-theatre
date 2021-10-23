package com.movietheatre.backend.service;

import com.movietheatre.backend.entities.StageName;
import com.movietheatre.backend.reposiory.StageNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StageNameService {
  private final StageNameRepository stageNameRepository;
  @Autowired
  public StageNameService(StageNameRepository stageNameRepository) {
    this.stageNameRepository = stageNameRepository;
  }

  public void addStageName(StageName name){
    stageNameRepository.save(name);
  }

}
