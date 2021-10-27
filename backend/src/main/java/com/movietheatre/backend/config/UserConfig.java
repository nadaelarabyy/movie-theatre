package com.movietheatre.backend.config;

import com.movietheatre.backend.entities.User;
import com.movietheatre.backend.reposiory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class UserConfig {
  private final PasswordEncoder passwordEncoder;

  @Bean
  CommandLineRunner commandLineRunner(UserRepository userRepository){
    return args -> {
//      email, password, role, inappropriate movies flagges
      User user1 = new User(
        "nadaelarabyy@yahoo.com",
        "nada@123","admin"
        ,new HashSet<>());
      user1.setPassword(passwordEncoder.encode(user1.getPassword()));
//      ------------------------------------------
      User user2= new User("salmanabil@gmail.com",
        "salma@123","viewer",
        new HashSet<>());
      user2.setPassword(passwordEncoder.encode(user2.getPassword()));
//      -------------------------------------------
      User user3= new User("sondoselshemy@gmail.com",
        "sondos@123","viewer",
        new HashSet<>());
      user3.setPassword(passwordEncoder.encode(user3.getPassword()));
//      -------------------------------------------
      User user4 = new User("yomnaabdouelfotoh@gmail.com",
        "yomna@123","viewer"
        ,new HashSet<>());
      user4.setPassword(passwordEncoder.encode(user4.getPassword()));
//      -------------------------------------------
      User user5 = new User("nadamagdy@gmail.com",
        "nada@12345","viewer",
        new HashSet<>());
      user5.setPassword(passwordEncoder.encode(user5.getPassword()));
//      -------------------------------------------
      List<User> users = List.of(user1,user2,user3,user4,user5);
      userRepository.saveAll(users);
    };
  }
}
