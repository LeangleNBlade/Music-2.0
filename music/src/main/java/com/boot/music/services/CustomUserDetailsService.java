package com.boot.music.services;

import com.boot.music.entity.Account;
import com.boot.music.repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService  {

    @Autowired
    private AccountRepo accRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = accRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist!"));
        return (UserDetails) user;
    }
}
