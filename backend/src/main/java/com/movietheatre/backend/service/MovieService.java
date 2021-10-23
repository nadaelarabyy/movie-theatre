package com.movietheatre.backend.service;

import com.movietheatre.backend.entities.Genre;
import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.entities.ProductionCompany;
import com.movietheatre.backend.entities.Review;
import com.movietheatre.backend.reposiory.MovieReposiory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
  private final MovieReposiory movieReposiory;
  private final ProductionCompanyService productionCompanyService;
  private final CastService castService;
  private final ReviewService reviewService;
  @Autowired
  public MovieService(MovieReposiory movieReposiory,
                      ProductionCompanyService productionCompanyService,
                      CastService castService,
                      ReviewService reviewService) {
    this.movieReposiory = movieReposiory;
    this.productionCompanyService = productionCompanyService;
    this.castService = castService;
    this.reviewService = reviewService;
  }
  public List<Movie> getAllMovies(){
    return movieReposiory.findAll()
      .stream()
      .filter(movie -> movie.getInappropriate()<=10)
      .collect(Collectors.toList());
  }
  public Movie getMovieById(Long id){
    return movieReposiory.findById(id).orElse(null);
  }
  public Movie getMovieDetailsById(Long id){
    JSONObject movieDetails = new JSONObject();
    String API_KEY = "8c6f102e3e869d116008630637634ce3";
    URL url;
    StringBuilder response = new StringBuilder();
    try {
        url = new URL("https://api.themoviedb.org/3/movie/"+id+"?api_key="+API_KEY+"&language=en-US");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();
        JSONObject movieObj = new JSONObject(response.toString());
        String path = movieObj.getString("poster_path") == null?
          "https://www.pngfind.com/pngs/m/554-5541387_null-poster-hd-png-download.png":
          "http://image.tmdb.org/t/p/w342"+movieObj.getString("poster_path");
        movieDetails.put("image_path",path);
        JSONArray genres = movieObj.getJSONArray("genres");
        movieDetails.put("genres",genres);
        JSONArray production = movieObj.getJSONArray("production_companies");
        movieDetails.put("companies",production);
        movieDetails.put("runtime",movieObj.getInt("runtime"));

    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }

    return movieReposiory.findById(id).map(movie -> {
      try {
        movie.setMovieLength(movieDetails.getInt("runtime"));
        movie.setImagePath(movieDetails.getString("image_path"));
//        JSONArray genres = movieDetails.getJSONArray("genres");
//        for(int i=0;genres.length()>i;i++){
//          Long genreId = genres.getJSONObject(i).getLong("id");
//          Genre genre = genreService.getGenreById(genreId);
//          genre.getMovies().add(movie);
//        }
//        ========================================================================================
        JSONArray companies = movieDetails.getJSONArray("companies");
        for(int i=0;companies.length()>i;i++){
          Long cId = companies.getJSONObject(i).getLong("id");
          ProductionCompany pCompany = productionCompanyService.getProductionCompanyById(cId);
          if(pCompany==null){
            Set<Movie>movies = new HashSet<>();
            movies.add(movie);
            ProductionCompany com = new ProductionCompany(
              cId,companies.getJSONObject(i).getString("name"),
              movies
            );
            productionCompanyService.addProductionCompany(com);
          }
          else{
            productionCompanyService.addMovie(cId,movie);
          }

        }
//        ============================================================================
//        adding cast to the details
        castService.addCastCrewToMovie(movie);
//        add reviews to the details
        Set<Review> reviews=reviewService.addReviewsToMovie(movie);
        movie.setReviews(reviews);
        movieReposiory.saveAndFlush(movie);


      } catch (JSONException e) {
        e.printStackTrace();
      }
      return movieReposiory.saveAndFlush(movie);
    }).orElse(null);
    // print result
  }

  public List<Movie> getMoviesPaginated(int pageNo, int pageSize){
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<Movie> pagedResult = movieReposiory.findAll(paging);
    return pagedResult.toList();
  }

  public List<Movie> getMovieGenres(Long movieId){
    Movie movie = getMovieById(movieId);
    if(movie!=null){
      List<Movie> moviesByGenre = movie.getGenres().stream().distinct()
        .map(Genre::getMovies)
        .flatMap(Collection::stream)
        .sorted(Comparator.comparing(Movie::getRating).reversed())
        .collect(Collectors.toList());
      List<Movie> moviesByGenreByLang = moviesByGenre.stream().distinct()
        .filter(movie1 -> movie1.getLanguage().equals(movie.getLanguage()))
        .collect(Collectors.toList());
//      return list of movies sorted by rating descending
      if(moviesByGenreByLang.size()>0)
        return moviesByGenreByLang;
      return moviesByGenre;
    }
    return null;

  }

  public Movie updateMovie(Movie movie){
    return movieReposiory.saveAndFlush(movie);
  }
  public List<Movie> getInappropriateMovies(){
    return movieReposiory.findAll().stream()
      .filter(movie -> movie.getInappropriate()>0)
      .collect(Collectors.toList());
  }


}

