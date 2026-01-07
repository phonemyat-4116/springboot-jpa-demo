package com.example.spring_data_jpa_demo.repositories;

import com.example.spring_data_jpa_demo.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicantJpaRepository extends JpaRepository<Applicant, Long> {
    List<Applicant> findByStatusOrderByNameAsc(String status);

    List<Applicant> findApplicantByName(String name);

}
