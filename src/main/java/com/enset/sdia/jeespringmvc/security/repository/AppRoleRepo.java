package com.enset.sdia.jeespringmvc.security.repository;

import com.enset.sdia.jeespringmvc.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
