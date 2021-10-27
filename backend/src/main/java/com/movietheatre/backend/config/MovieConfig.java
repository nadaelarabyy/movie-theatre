package com.movietheatre.backend.config;

import com.movietheatre.backend.entities.*;
import com.movietheatre.backend.reposiory.GenreRepository;
import com.movietheatre.backend.reposiory.MovieReposiory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
public class MovieConfig {
  @Bean
  CommandLineRunner commandLineRunnerMovie(MovieReposiory movieReposiory,
                                           GenreRepository genreRepository){
    return args -> {
      generateGenres(genreRepository);
      getTopRated(movieReposiory,genreRepository);



    };
  }

  private void getTopRated(MovieReposiory movieReposiory,
                                  GenreRepository genreRepository){
    String API_KEY = "8c6f102e3e869d116008630637634ce3";
    URL url;


    try {
      int pages = 5;
      int num=1;
      while (pages>=num){
        String dest = "https://api.themoviedb.org/3/movie/top_rated?api_key="+
          API_KEY
          +"&page="+num;
        url = new URL(dest);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
          new InputStreamReader(con.getInputStream())
        );
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();
        num++;
        JSONObject movieObj = new JSONObject(response.toString());
        JSONArray movieArr = movieObj.getJSONArray("results");
        convertJsonToList(movieArr,genreRepository,movieReposiory);
      }
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }

    // print result
  }
  private void convertJsonToList(JSONArray movieArr,
                                        GenreRepository genreRepository,
                                        MovieReposiory movieReposiory){
    for(int i=0;movieArr.length()>i;i++){
      try {
        JSONObject movieObj = movieArr.getJSONObject(i);
        Long movieId = movieObj.getLong("id");

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate = format.parse(movieObj.getString("release_date"));
        Movie movie = new Movie(
          movieId,
          movieObj.getString("title"),
          movieObj.getString("original_language"),
          0,
          movieObj.getString("overview"),
          movieObj.getDouble("vote_average"),
          movieObj.getInt("vote_count"),"",
          "image path",
          new HashSet<>(),
          new HashSet<>(),
          new HashSet<>(),
          new HashSet<>(),
          0,
          false,
          new HashSet<>(),
          releaseDate,
          "tmdb");
        movieReposiory.save(movie);
        JSONArray genreArr = movieObj.getJSONArray("genre_ids");
        Set<Genre> genreSet = new HashSet<>();
        for(int j=0;genreArr.length()>j;j++){
          Genre genre = genreRepository.findById(genreArr.getLong(j)).orElse(null);
          if(genre!=null){

            genreSet.add(genre);
            genre.getMovies().add(movie);
            genreRepository.saveAndFlush(genre);

          }
        }
        movie.getGenres().addAll(genreSet);
        movieReposiory.saveAndFlush(movie);
      } catch (JSONException | ParseException e) {
        e.printStackTrace();
      }
    }
  }

//  ==========================Genre=================================================================
  private void generateGenres(GenreRepository genreRepository){
    try {
      String response = getGenreResponse();
      JSONObject jsonObj = new JSONObject(response);
      JSONArray genresArr = jsonObj.getJSONArray("genres");
      List<Genre> genreList = new ArrayList<>();
      for (int i = 0; genresArr.length() > i; i++) {
        JSONObject genreObj = genresArr.getJSONObject(i);
        Genre genre = new Genre(
          genreObj.getLong("id"),
          genreObj.getString("name"),
          new HashSet<>()
        );
        genreList.add(genre);
      }
      genreRepository.saveAll(genreList);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
private String getGenreResponse(){
  String API_KEY = "8c6f102e3e869d116008630637634ce3";
  URL url;
  StringBuilder response = new StringBuilder();

  try {
    url = new URL("https://api.themoviedb.org/3/genre/movie/list?api_key="+API_KEY+"&language=en-US");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
  } catch (IOException e) {
    e.printStackTrace();
  }
  // print result
  return response.toString();

}
}
