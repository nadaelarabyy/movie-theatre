package com.movietheatre.backend.reposiory;

import com.movietheatre.backend.TestConfig;
import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.entities.Review;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import({TestConfig.class})
class ReviewRepositoryTest {
  @Autowired
  TestEntityManager entityManager;
  @Autowired
  ReviewRepository reviewRepository;
  @Autowired
  MovieReposiory movieReposiory;
  //  find no review if repo is empty
  @Test
  public void find_no_review_if_repo_is_empty(){
    List<Review> reviewList = reviewRepository.findAll();
    assertEquals(reviewList.size(),0);
  }

  //  save a review
  @Test
  public void save_a_review(){
    Review review = getReview();
    Review reviewReturned = reviewRepository.save(review);
    assertEquals(reviewReturned.getId(),review.getId());
  }
  @Test
  //  delete all reviews
  public void delete_all_reviews(){
    Review review = getReview();
    entityManager.persist(review);
    entityManager.flush();
    reviewRepository.deleteAll();
    assertEquals(reviewRepository.findAll().size(),0);

  }
  //  find all reviews
  @Test
  public void find_all_reviews(){
    Review review = getReview();
    entityManager.persist(review);
    List<Review> reviewList = reviewRepository.findAll();
    assertEquals(1, reviewList.size());
    assertTrue(reviewList.contains(review));

  }

  //  find review by id
  @Test
  public void find_review_by_id(){
    Review review = getReview();
    entityManager.persist(review);
    Review reviewReturned = reviewRepository.findById(review.getId()).get();
    assertEquals(reviewReturned.getAuthorName(), review.getAuthorName());

  }

  public Review getReview(){
    Movie movie = new Movie(-1L, "mock title", "en", 90, "mock descritpion",
      2.2, 15, "mock director", "mock image path", new HashSet<>(),
      new HashSet<>(), new HashSet<>(), new HashSet<>(),
      0, false,new HashSet<>(),new Date(),"tmdb");
    return new Review("mock author name", "27-12-2020", "mock content",
      movie);
  }

}
