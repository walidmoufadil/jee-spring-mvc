package com.enset.sdia.jeespringmvc.security.repository;

import com.enset.sdia.jeespringmvc.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, String> {
    AppUser findAppUsersByUserName(String username);
}
