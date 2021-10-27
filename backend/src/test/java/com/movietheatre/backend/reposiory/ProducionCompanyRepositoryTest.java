package com.movietheatre.backend.reposiory;

import com.movietheatre.backend.TestConfig;
import com.movietheatre.backend.entities.ProductionCompany;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
class ProducionCompanyRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private ProducionCompanyRepository producionCompanyRepository;

  //  save a production company
  @Test
  public void save_a_prod_company(){
    ProductionCompany productionCompany = getProductionCompany();
    ProductionCompany prodCompanyRepoReturned = producionCompanyRepository.save(productionCompany);
    assertEquals(prodCompanyRepoReturned.getId(),productionCompany.getId());
  }
  @Test
  //  delete all production companies
  public void delete_all_prod_companies(){
    ProductionCompany productionCompany1 = getProductionCompany();
    productionCompany1.setId(-2L);
    ProductionCompany productionCompany2 = getProductionCompany();
    productionCompany2.setId(-3L);
    entityManager.persist(productionCompany1);
    entityManager.persist(productionCompany2);
    producionCompanyRepository.deleteAll();
    assertEquals(producionCompanyRepository.findAll().size(),0);

  }
  //  find all production companies
  @Test
  public void find_all_prod_companies(){
    ProductionCompany productionCompany1 = getProductionCompany();
    productionCompany1.setId(-4L);
    entityManager.persist(productionCompany1);
    ProductionCompany productionCompany2 = getProductionCompany();
    productionCompany2.setId(-5L);
    entityManager.persist(productionCompany2);
    ProductionCompany productionCompany3 = getProductionCompany();
    productionCompany3.setId(-6L);
    entityManager.persist(productionCompany3);
    List<ProductionCompany> productionCompanyList = producionCompanyRepository.findAll();
    assertEquals(3, productionCompanyList.size());
    assertTrue(productionCompanyList.contains(productionCompany1));
    assertTrue(productionCompanyList.contains(productionCompany2));

  }

  //  find production company by id
  @Test
  public void find_prod_company_by_id(){
    ProductionCompany productionCompany1 = getProductionCompany();
    productionCompany1.setId(-200L);
    productionCompany1.setName("Mock prod name 1");
    entityManager.persist(productionCompany1);
    ProductionCompany productionCompany2 = getProductionCompany();
    productionCompany2.setId(-300L);
    productionCompany2.setName("Mock title 2");
    entityManager.persist(productionCompany2);
    ProductionCompany productionCompanyReturned = producionCompanyRepository.findById(productionCompany2.getId()).get();
    assertEquals(productionCompanyReturned.getName(), productionCompany2.getName());

  }


  public ProductionCompany getProductionCompany(){
    return new ProductionCompany(-1L,"mock prod company 1",new HashSet<>());
  }

}
