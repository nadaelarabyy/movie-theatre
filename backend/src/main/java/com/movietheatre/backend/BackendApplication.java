package com.movietheatre.backend;

import com.movietheatre.backend.security.MyUserDetailService;
import com.movietheatre.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendApplication {
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
  @Bean
  PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
  @Autowired
  private MyUserDetailService userDetailsService;

  public static void main(String[] args) {

    SpringApplication.run(BackendApplication.class, args);
  }



}
