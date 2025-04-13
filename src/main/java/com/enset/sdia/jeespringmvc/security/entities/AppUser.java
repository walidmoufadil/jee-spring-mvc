package com.enset.sdia.jeespringmvc.security.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @NoArgsConstructor @AllArgsConstructor @Data @Builder
public class AppUser {
    @Id
    private String userId;
    private String userName;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles;
}
