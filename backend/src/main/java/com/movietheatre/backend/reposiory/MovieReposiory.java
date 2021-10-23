package com.movietheatre.backend.reposiory;

import com.movietheatre.backend.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieReposiory extends JpaRepository<Movie,Long> {

}
