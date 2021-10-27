package com.movietheatre.backend.reposiory;

import com.movietheatre.backend.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MovieReposiory extends JpaRepository<Movie,Long> {
  @Modifying
  @Query(value = "delete from movies_genres mg where mg.movie_id=?1",nativeQuery = true)
  void deleteGenresByMovieId(Long id);

}
