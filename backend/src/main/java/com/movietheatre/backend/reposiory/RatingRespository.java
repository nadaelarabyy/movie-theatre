package com.movietheatre.backend.reposiory;

import com.movietheatre.backend.entities.Rate;
import com.movietheatre.backend.entities.RateId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRespository extends JpaRepository<Rate, RateId> {
}
