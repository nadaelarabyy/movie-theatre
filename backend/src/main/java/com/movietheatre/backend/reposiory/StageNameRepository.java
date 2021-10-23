package com.movietheatre.backend.reposiory;

import com.movietheatre.backend.entities.StageName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageNameRepository extends JpaRepository<StageName,Long> {

}
