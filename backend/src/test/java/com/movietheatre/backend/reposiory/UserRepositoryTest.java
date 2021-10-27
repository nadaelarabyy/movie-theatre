package com.movietheatre.backend.reposiory;

import com.movietheatre.backend.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private UserRepository userRepository;
  //  save a user
  @Test
  public void save_a_user(){
    User user = getUser();
    User userReturned = userRepository.save(user);
    assertEquals(userReturned.getId(),user.getId());
  }
  @Test
  //  delete all users
  public void delete_all_stage_names(){
    User user = getUser();
    entityManager.persist(user);
    entityManager.flush();
    userRepository.deleteAll();
    assertEquals(userRepository.findAll().size(),0);

  }
  //  find all users
  @Test
  public void find_all_users(){
    User user = getUser();
    entityManager.persist(user);
    List<User> users = userRepository.findAll();
    assertEquals(1, users.size());
    assertTrue(users.contains(user));

  }

  //  find user by id
  @Test
  public void find_user_by_id(){
    User user = getUser();
    entityManager.persist(user);
    User userReturned = userRepository.findById(user.getId()).get();
    assertEquals(userReturned.getEmail(), user.getEmail());

  }

  public User getUser(){
    return new User("mock@email.com", "mock1234", "viewer",
      new HashSet<>());
  }

}
