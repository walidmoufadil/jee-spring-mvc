package com.enset.sdia.jeespringmvc.security.service;

import com.enset.sdia.jeespringmvc.security.entities.AppRole;
import com.enset.sdia.jeespringmvc.security.entities.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = accountService.loadUserByUsername(username);
        if(user == null) throw new UsernameNotFoundException(username);
        UserDetails  userDetails = User.withUsername(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(AppRole::getRole).toArray(String[]::new))
                .build();
        return userDetails;
    }
}
