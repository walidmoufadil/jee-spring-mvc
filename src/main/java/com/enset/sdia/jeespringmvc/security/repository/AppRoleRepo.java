package com.enset.sdia.jeespringmvc.security.repository;

import com.enset.sdia.jeespringmvc.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleRepo extends JpaRepository<AppRole, String> {

}
