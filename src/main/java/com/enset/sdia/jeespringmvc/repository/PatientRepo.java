package com.enset.sdia.jeespringmvc.repository;

import com.enset.sdia.jeespringmvc.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> {
    Page<Patient> findByNomContains(String nom, Pageable pageable);
}
