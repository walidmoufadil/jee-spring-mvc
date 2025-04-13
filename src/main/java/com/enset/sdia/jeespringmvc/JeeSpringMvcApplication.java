package com.enset.sdia.jeespringmvc;

import com.enset.sdia.jeespringmvc.entity.Patient;
import com.enset.sdia.jeespringmvc.repository.PatientRepo;
import com.enset.sdia.jeespringmvc.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class JeeSpringMvcApplication implements CommandLineRunner {
    @Autowired
    private PatientRepo patientRepo;

    public static void main(String[] args) {
        SpringApplication.run(JeeSpringMvcApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        patientRepo.save(
                Patient.builder()
                        .nom("Doe")
                        .malade(Math.random()>0.5)
                        .score((int) Math.random() * 100)
                        .dateNaissance(new Date())
                        .build()
        );
        patientRepo.save(
                Patient.builder()
                        .nom("Cartner")
                        .malade(Math.random()>0.5)
                        .score((int) Math.random() * 100 )
                        .dateNaissance(new Date())
                        .build()
        );
        patientRepo.save(
                Patient.builder()
                        .nom("John")
                        .malade(Math.random()>0.5)
                        .score((int) Math.random() * 100)
                        .dateNaissance(new Date())
                        .build()
        );
        patientRepo.save(
                Patient.builder()
                        .nom("Maria")
                        .malade(Math.random()>0.5)
                        .score((int) Math.random() * 100)
                        .dateNaissance(new Date())
                        .build()
        );
        patientRepo.save(
                Patient.builder()
                        .nom("Doe")
                        .malade(Math.random()>0.5)
                        .score((int) Math.random() * 100)
                        .dateNaissance(new Date())
                        .build()
        );
        patientRepo.save(
                Patient.builder()
                        .nom("Cartner")
                        .malade(Math.random()>0.5)
                        .score((int) Math.random() * 100)
                        .dateNaissance(new Date())
                        .build()
        );
        patientRepo.save(
                Patient.builder()
                        .nom("Doe")
                        .malade(Math.random()>0.5)
                        .score((int) Math.random() * 100)
                        .dateNaissance(new Date())
                        .build()
        );
        patientRepo.save(
                Patient.builder()
                        .nom("Cartner")
                        .malade(Math.random()>0.5)
                        .score((int) Math.random() * 100)
                        .dateNaissance(new Date())
                        .build()
        );
        patientRepo.save(
                Patient.builder()
                        .nom("Doe")
                        .malade(Math.random()>0.5)
                        .score((int) Math.random() * 100)
                        .dateNaissance(new Date())
                        .build()
        );
        patientRepo.save(
                Patient.builder()
                        .nom("Cartner")
                        .malade(Math.random()>0.5)
                        .score((int) Math.random() * 100)
                        .dateNaissance(new Date())
                        .build()
        );

    }

    //@Bean
    CommandLineRunner commandLineRunner (JdbcUserDetailsManager jdbcUserDetailsManager){
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {
            if(!jdbcUserDetailsManager.userExists("user2"))
                jdbcUserDetailsManager.createUser(User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("user").build());

            if(!jdbcUserDetailsManager.userExists("admin2"))
                jdbcUserDetailsManager.createUser(User.withUsername("admin2").password(passwordEncoder.encode("1234")).roles("user","admin").build());

        };
    }

    @Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
        return args -> {
            accountService.addNewRole("user");
            accountService.addNewRole("admin");
            accountService.addNewUser("user3","1234","user3@gmail.com","1234");
            accountService.addNewUser("admin3","1234","admin3@gmail.com","1234");

            accountService.addRoleToUser("user3","user");
            accountService.addRoleToUser("admin3","admin");

        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
