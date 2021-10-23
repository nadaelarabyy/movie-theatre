package com.movietheatre.backend.service;

import com.movietheatre.backend.entities.Cast;
import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.entities.StageName;
import com.movietheatre.backend.reposiory.CastRepository;
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
import java.util.HashSet;

@Service
public class CastService {
  private final CastRepository castRepository;
  private final StageNameService stageNameService;
  @Autowired
  public CastService(CastRepository castRepository,
                     StageNameService stageNameService) {
    this.castRepository = castRepository;
    this.stageNameService = stageNameService;
  }
  public Cast getCastById(Long castId){
    return castRepository.findById(castId).orElse(null);
  }
  public void addCastCrewToMovie(Movie movie){
    String API_KEY = "8c6f102e3e869d116008630637634ce3";
    URL url;
    StringBuilder response = new StringBuilder();
    try {
      url = new URL("https://api.themoviedb.org/3/movie/"+movie.getId()+"/credits?api_key="+API_KEY+"&language=en-US");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      JSONObject castCrewObj = new JSONObject(response.toString());
//      add cast to the movie
      addCast(castCrewObj,movie);
//      add crew to the movie
      addCrew(castCrewObj,movie);
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }
  }
  public void addCast(JSONObject castCrewObj,Movie movie){
    try {
      JSONArray cast = castCrewObj.getJSONArray("cast");
      int size = cast.length()>10?10:cast.length();
      for (int i = 0; i < size; i++) {
        JSONObject castObj = cast.getJSONObject(i);
        String path = castObj.getString("profile_path") == null ?
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4dAzC_zHw3FvK6qS8_YxygrIVP_HvYLv1tA&usqp=CAU" :
          "http://image.tmdb.org/t/p/w92" + castObj.getString("profile_path");
        Cast oldCast = this.getCastById(castObj.getLong("id"));
        if (oldCast == null) {
          Cast newCast = new Cast(castObj.getLong("id"), path,
            castObj.getString("name"), new HashSet<>(), new HashSet<>());
          StageName newStageName = new StageName(
            castObj.getString("character"),
            newCast);
          stageNameService.addStageName(newStageName);
          newCast.getStageNameList().add(newStageName);
          newCast.getMovies().add(movie);

          castRepository.save(newCast);
        } else {
//          add a new stage name
          StageName newStageName = new StageName(
            castObj.getString("character"),
            oldCast);
          stageNameService.addStageName(newStageName);
          oldCast.getStageNameList().add(newStageName);
          castRepository.save(oldCast);
        }

      }
    }
    catch (JSONException e){
      e.printStackTrace();

    }

  }
  public void addCrew(JSONObject castCrewObj,Movie movie) {
    try {
      JSONArray crew = castCrewObj.getJSONArray("crew");
      for (int i = 0; crew.length() > i; i++) {
        JSONObject crew_member = crew.getJSONObject(i);
        if (crew_member.getString("job").equals("Director")) {
          movie.setDirector(crew_member.getString("name"));
          break;
        }
      }
    }
    catch (JSONException e ){
      e.printStackTrace();
    }

  }

}
