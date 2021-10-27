package com.movietheatre.backend.service;

import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.entities.Review;
import com.movietheatre.backend.reposiory.ReviewRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class ReviewService {
  private final ReviewRepository reviewRepository;
  @Autowired
  public ReviewService(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  public Set<Review> addReviewsToMovie(Movie movie){
    String API_KEY = "8c6f102e3e869d116008630637634ce3";
    URL url;
    StringBuilder response = new StringBuilder();
    Set<Review> reviewList = new HashSet<>();

    try {
      url = new URL("https://api.themoviedb.org/3/movie/"+movie.getId()+"/reviews?api_key="+API_KEY+"&language=en-US&page=1");
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
      for(int i=0;i<reviews.length();i++){
        JSONObject reviewObj = reviews.getJSONObject(i);
        String authorName = reviewObj.getString("author");
        String content = reviewObj.getString("content");
//
        String issueDate = reviewObj.getString("created_at");
        Review newReview = new Review(authorName,issueDate,content,movie);
        reviewList.add(newReview);
      }
      reviewRepository.saveAll(reviewList);

    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }
    return reviewList;

  }
}
