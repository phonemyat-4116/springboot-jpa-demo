package com.example.spring_data_jpa_demo.repositories;

import com.example.spring_data_jpa_demo.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeJpaRepository extends JpaRepository<Resume, Long> {

}
