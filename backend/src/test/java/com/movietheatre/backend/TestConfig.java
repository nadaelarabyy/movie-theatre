package com.movietheatre.backend;

import com.movietheatre.backend.security.MyUserDetailService;
import com.movietheatre.backend.service.MovieService;
import com.movietheatre.backend.service.UserService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {
//  @MockBean
//  private UserService userService;
  @Bean
  public MyUserDetailService userDetailsService() {
    UserService userService = mock(UserService.class);

    return new MyUserDetailService(userService);
  }
  @Bean
  BCryptPasswordEncoder passwordEncoderTest(){
    return new BCryptPasswordEncoder();
  }
}
