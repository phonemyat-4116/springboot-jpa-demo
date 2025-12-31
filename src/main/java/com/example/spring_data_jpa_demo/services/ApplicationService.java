package com.example.spring_data_jpa_demo.services;

import com.example.spring_data_jpa_demo.entity.Applicant;
import com.example.spring_data_jpa_demo.entity.Job;
import com.example.spring_data_jpa_demo.entity.Resume;
import com.example.spring_data_jpa_demo.repositories.*;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService {

    private ApplicantCrudRepository  applicantCrudRepository;
    private ApplicantPaginationAndSortingRepository applicantPaginationAndSortingRepository;
    private ApplicantJpaRepository applicantJpaRepository;
    private ResumeJpaRepository resumeJpaRepository;
    private JobJpaRepository jobJpaRepository;

    @Autowired
    public ApplicationService(
            ApplicantCrudRepository applicantCrudRepository,
            ApplicantPaginationAndSortingRepository applicantPaginationAndSortingRepository,
            ApplicantJpaRepository applicantJpaRepository,
            ResumeJpaRepository resumeJpaRepository,
            JobJpaRepository jobJpaRepository) {
        this.applicantCrudRepository = applicantCrudRepository;
        this.applicantPaginationAndSortingRepository = applicantPaginationAndSortingRepository;
        this.applicantJpaRepository = applicantJpaRepository;
        this.resumeJpaRepository = resumeJpaRepository;
        this.jobJpaRepository = jobJpaRepository;
    }


    public List<Applicant> getAllApplicants() {
        // CrudRepo will return Iterable
        Iterable<Applicant> allApplicants = applicantCrudRepository.findAll();

        List<Applicant> applicantList = new ArrayList<>();
        allApplicants.forEach(applicantList::add);

        return applicantList;
    }

    public Applicant saveApplicant(Applicant applicant) {
        return applicantCrudRepository.save(applicant);
    }

    // pagination
    public Iterable<Applicant> getApplicantWithPagination(int page, int size) {
        return applicantPaginationAndSortingRepository.findAll(PageRequest.of(page, size));
    }

    public List<Applicant> getApplicantByStatus(String status) {
        return applicantJpaRepository.findByStatusOrderByNameAsc(status);
    }

    @Transactional
    public Resume saveResume(Long applicantId, Resume resume) {
        Applicant applicant = applicantJpaRepository.findById(applicantId)
                .orElseThrow(() -> new RuntimeException("Applicant is not found"));

        resume.setApplicant(applicant);

        return resumeJpaRepository.save(resume);

    }

    public Job saveJob(Job job) {
        return jobJpaRepository.save(job);
    }

    @Transactional
    public Applicant applyJob(Long applicantId, Long jobId) {
        Applicant applicant = applicantJpaRepository.findById(applicantId)
                .orElseThrow(() -> new RuntimeException("Applicant is not found"));

        Job job = jobJpaRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job is not found"));

        applicant.applyJob(job);

        return applicantJpaRepository.save(applicant);
    }
}
