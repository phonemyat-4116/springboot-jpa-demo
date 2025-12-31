package com.example.spring_data_jpa_demo.repositories;

import com.example.spring_data_jpa_demo.entity.Applicant;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantPaginationAndSortingRepository extends PagingAndSortingRepository<Applicant, Long> {

}
