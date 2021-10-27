package com.movietheatre.backend.reposiory;

import com.movietheatre.backend.entities.Rate;
import com.movietheatre.backend.entities.RateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRespository extends JpaRepository<Rate, RateId> {
}
