package com.example.spring_data_jpa_demo.repositories;

import com.example.spring_data_jpa_demo.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantJpaRepository extends JpaRepository<Applicant, Long> {
    List<Applicant> findByStatusOrderByNameAsc(String status);

}
