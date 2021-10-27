package com.movietheatre.backend.security;

import com.movietheatre.backend.filters.CustomAuthenticationFilter;
import com.movietheatre.backend.filters.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
      .passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests().antMatchers("/api/users/logout").permitAll();
    http.authorizeRequests().antMatchers("/api/users/token/refresh").permitAll();
    http.authorizeRequests().antMatchers("/api/movies/**").permitAll();
    http.authorizeRequests().antMatchers("/api/genres/**").permitAll();
//    admin
//    1- show/hide movies
    http.authorizeRequests().antMatchers("/api/users/show")
      .hasAnyAuthority("admin");
//    2- see movies recieved inappropriate flags
    http.authorizeRequests().antMatchers("/api/users/flagged")
      .hasAnyAuthority("admin");
//    3-edit movie (language/ genre/ release date)
    http.authorizeRequests().antMatchers("/api/users/editmovie/**")
      .hasAnyAuthority("admin");
//    4-add movie details
    http.authorizeRequests().antMatchers("/api/users/addmovie")
      .hasAnyAuthority("admin");
//    viewer
//    1-rate a movie
    http.authorizeRequests().antMatchers("/api/users/rate")
      .hasAnyAuthority("viewer");
//    2-flag a movie
    http.authorizeRequests().antMatchers("/api/users/flag")
      .hasAnyAuthority("viewer");
    http.authorizeRequests().anyRequest().authenticated();
    http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
    http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
  }



  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception{
    return super.authenticationManagerBean();
  }
}
