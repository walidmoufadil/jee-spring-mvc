package com.enset.sdia.jeespringmvc;

import com.enset.sdia.jeespringmvc.entity.Patient;
import com.enset.sdia.jeespringmvc.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
