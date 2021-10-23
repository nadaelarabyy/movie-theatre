package com.movietheatre.backend.reposiory;

import com.movietheatre.backend.entities.Cast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CastRepository extends JpaRepository<Cast,Long> {
}
