package com.enset.sdia.jeespringmvc.security.service;

import com.enset.sdia.jeespringmvc.security.entities.AppRole;
import com.enset.sdia.jeespringmvc.security.entities.AppUser;
import com.enset.sdia.jeespringmvc.security.repository.AppRoleRepo;
import com.enset.sdia.jeespringmvc.security.repository.AppUserRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    AppUserRepo appUserRepo;
    AppRoleRepo appRoleRepo;
    PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        if(appUserRepo.findAppUsersByUserName(username) != null)
            throw new RuntimeException("Username already exists");
        if(!password.equals(confirmPassword))
            throw new RuntimeException("Passwords do not match");
        AppUser user = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .userName(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        return appUserRepo.save(user);
    }

    @Override
    public AppRole addNewRole(String roleName) {
        AppRole role = appRoleRepo.findById(roleName).orElse(null);
        if(role != null)
            throw new RuntimeException("Role already exists");

        return appRoleRepo.save(AppRole
                        .builder()
                        .role(roleName)
                        .build());
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = appUserRepo.findAppUsersByUserName(username);
        AppRole role = appRoleRepo.findById(roleName).orElseThrow(()-> new RuntimeException("Role does not exist"));
        user.getRoles().add(role);

    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser user = appUserRepo.findAppUsersByUserName(username);
        AppRole role = appRoleRepo.findById(roleName).orElseThrow(()-> new RuntimeException("Role does not exist"));
        user.getRoles().remove(role);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepo.findAppUsersByUserName(username);
    }
}

