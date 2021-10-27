package com.movietheatre.backend.security;

import com.movietheatre.backend.entities.User;
import com.movietheatre.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class MyUserDetailService implements UserDetailsService {
  private final UserService userService;
  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userService.getUserByEmail(s);
    if(user == null){
      log.error("User not found in the database");
      throw new UsernameNotFoundException("User not found in the database");
    }
    else{
      log.info("User found in the database: {}",s);

    }
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(user.getRole()));
    return new org.springframework.security.core.userdetails.User(
      user.getEmail(),
      user.getPassword(),
      authorities
    );
  }
}
