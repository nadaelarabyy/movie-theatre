package com.movietheatre.backend.service;

import com.movietheatre.backend.entities.Cast;
import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.reposiory.CastRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CastServiceTest {
  @Mock
  private CastRepository castRepository;
  @Mock
  private StageNameService stageNameService;
  @Spy
  @InjectMocks
  private CastService castService;
  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getCastById() {
    Cast cast = new Cast(1L, "mock image path", "mock name",
      new HashSet<>(), new HashSet<>());
    Mockito.when(castRepository.findById(1L)).thenReturn(Optional.of(cast));
    Cast testRes = castService.getCastById(1L);
    assertEquals(cast.getId(),testRes.getId());
    assertEquals(cast.getName(),testRes.getName());
    assertEquals(cast.getImagePath(),testRes.getImagePath());
    assertEquals(cast.getMovies(),testRes.getMovies());
    assertEquals(cast.getStageNameList(),testRes.getStageNameList());

  }
  @Test
  public void getById_return_null(){
    Cast cast = new Cast(1L, "mock image path", "mock name",
      new HashSet<>(), new HashSet<>());
    Mockito.when(castRepository.findById(1L)).thenReturn(Optional.of(cast));
    Cast testRes = castService.getCastById(1000L);
    assertNull(testRes);
  }

  @Test
  public void addCastCrewToMovie() {
//    CastService service = mock(CastService.class);
    Movie movie = getMovie();
    castService.addCastCrewToMovie(movie);
    verify(castService).addCastCrewToMovie(movie);
  }
  @Test
  public void addCast(){
    Movie movie = getMovie();
    JSONObject obj = getObject(movie);
    castService.addCast(obj,movie);
    verify(castService).addCast(obj,movie);
  }
  @Test
  public void addCrew(){
    Movie movie = getMovie();
    JSONObject obj = getObject(movie);
    castService.addCrew(obj,movie);
    verify(castService).addCrew(obj,movie);


  }
  public JSONObject getObject(Movie movie){
    String API_KEY = "8c6f102e3e869d116008630637634ce3";
    URL url;
    StringBuilder response = new StringBuilder();
    try {
      url = new URL("https://api.themoviedb.org/3/movie/" + movie.getId() + "/credits?api_key=" + API_KEY + "&language=en-US");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      JSONObject castCrewObj = new JSONObject(response.toString());
      return castCrewObj;
    } catch (JSONException | IOException e) {
      e.printStackTrace();
    }
    return new JSONObject();
  }

  public Movie getMovie(){
    return new Movie(19404L, "mock title", "en", 90, "mock descritpion",
      2.2, 15, "mock director", "mock image path", new HashSet<>(),
      new HashSet<>(), new HashSet<>(), new HashSet<>(),
      0, false,new HashSet<>(),new Date(),"tmdb");
  }


}
