package com.movietheatre.backend.service;

import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.entities.Review;
import com.movietheatre.backend.reposiory.ReviewRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReviewServiceTest {
  @Mock
  private ReviewRepository reviewRepository;
  @Spy
  @InjectMocks
  private ReviewService reviewService;
  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void addReviewsToMovie() {
    Movie movie = getMovie();
    Review newReview = getReview(movie);
    List<Review> reSet = new ArrayList<>();
    reSet.add(newReview);
    when(reviewRepository.saveAll(reSet)).thenReturn(reSet);
    List<Review> reviewList = reviewService.addReviewsToMovie(movie).stream().collect(Collectors.toList());
    assertEquals(reviewList.get(0).getAuthorName(),reSet.get(0).getAuthorName());
    assertEquals(reviewList.get(0).getContent(),reSet.get(0).getContent());
  }
  public Movie getMovie(){
    return new Movie(19404L, "mock title", "en", 90, "mock descritpion",
      2.2, 15, "mock director", "mock image path", new HashSet<>(),
      new HashSet<>(), new HashSet<>(), new HashSet<>(),
      0, false,new HashSet<>(),new Date(),"tmdb");
  }
  public Review getReview(Movie movie){
    URL url;
    StringBuilder response = new StringBuilder();
    String API_KEY = "8c6f102e3e869d116008630637634ce3";
    try {
      url = new URL("https://api.themoviedb.org/3/movie/" + movie.getId() + "/reviews?api_key=" +
        API_KEY + "&language=en-US&page=1");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      JSONObject reviewsObj = new JSONObject(response.toString());
      JSONArray reviews = reviewsObj.getJSONArray("results");
      for (int i = 0; i < reviews.length(); i++) {
        JSONObject reviewObj = reviews.getJSONObject(i);
        String authorName = reviewObj.getString("author");
        String content = reviewObj.getString("content");
//
        String issueDate = reviewObj.getString("created_at");
        Review newReview = new Review(authorName, issueDate, content, movie);
        return newReview;
      }
    } catch (JSONException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
