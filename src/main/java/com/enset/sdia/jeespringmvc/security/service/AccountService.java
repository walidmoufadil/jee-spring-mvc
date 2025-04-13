package com.enset.sdia.jeespringmvc.security.service;

import com.enset.sdia.jeespringmvc.security.entities.AppRole;
import com.enset.sdia.jeespringmvc.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addNewRole(String roleName);
    void addRoleToUser(String username, String roleName);
    void removeRoleFromUser(String username, String roleName);
    AppUser loadUserByUsername(String username);

}
