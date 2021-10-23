package com.movietheatre.backend.reposiory;

import com.movietheatre.backend.entities.ProductionCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProducionCompanyRepository extends JpaRepository<ProductionCompany,Long> {

}
