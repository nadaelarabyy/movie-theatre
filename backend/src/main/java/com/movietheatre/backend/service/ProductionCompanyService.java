package com.movietheatre.backend.service;

import com.movietheatre.backend.entities.Movie;
import com.movietheatre.backend.entities.ProductionCompany;
import com.movietheatre.backend.reposiory.ProducionCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductionCompanyService {
  private final ProducionCompanyRepository producionCompanyRepository;
  @Autowired
  public ProductionCompanyService(ProducionCompanyRepository producionCompanyRepository) {
    this.producionCompanyRepository = producionCompanyRepository;
  }
  public ProductionCompany getProductionCompanyById(Long id){
    return producionCompanyRepository.findById(id).orElse(null);
  }
  public void addProductionCompany(ProductionCompany productionCompany){
    producionCompanyRepository.save(productionCompany);
  }
  public void addMovie(Long id,Movie movie){
    producionCompanyRepository.findById(id).map(productionCompany -> {
      productionCompany.getMovies().add(movie);
      return producionCompanyRepository.save(productionCompany);
    });
  }
}
