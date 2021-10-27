package com.movietheatre.backend.service;

import com.movietheatre.backend.TestConfig;
import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.entities.ProductionCompany;
import com.movietheatre.backend.reposiory.ProducionCompanyRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import({TestConfig.class})
class ProductionCompanyServiceTest {
  @Mock
  private ProducionCompanyRepository producionCompanyRepository;
  @Spy
  @InjectMocks
  private ProductionCompanyService productionCompanyService;
  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
  }
  @Test
  public void getProductionCompanyById() {
    ProductionCompany productionCompany = getProdCompany();
    when(producionCompanyRepository.findById(1L)).thenReturn(Optional.of(productionCompany));
    ProductionCompany prodRes = productionCompanyService.getProductionCompanyById(1L);
    assertEquals(prodRes.getName(),productionCompany.getName());
  }

  @Test
  public void addProductionCompany() {
    ProductionCompany productionCompany = getProdCompany();
    when(producionCompanyRepository.save(productionCompany)).thenReturn(productionCompany);
    productionCompanyService.addProductionCompany(productionCompany);
    verify(producionCompanyRepository,atLeastOnce()).save(productionCompany);
  }

  @Test
  public void addMovie() {
    Movie movie = getMovie();
    ProductionCompany productionCompany = getProdCompany();
    when(producionCompanyRepository.save(productionCompany)).thenReturn(productionCompany);
    productionCompanyService.addMovie(1L,movie);
    verify(producionCompanyRepository,atLeastOnce()).findById(1L);
  }

  public ProductionCompany getProdCompany(){
    return new ProductionCompany(1L, "mock production name",
      new HashSet<>());
  }
  public Movie getMovie(){
    return new Movie(19404L, "mock title", "en", 90, "mock descritpion",
      2.2, 15, "mock director", "mock image path", new HashSet<>(),
      new HashSet<>(), new HashSet<>(), new HashSet<>(),
      0, false,new HashSet<>(),new Date(),"tmdb");
  }

}
