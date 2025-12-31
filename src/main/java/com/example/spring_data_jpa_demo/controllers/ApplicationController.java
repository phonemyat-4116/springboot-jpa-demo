package com.example.spring_data_jpa_demo.controllers;

import com.example.spring_data_jpa_demo.entity.Applicant;
import com.example.spring_data_jpa_demo.entity.Job;
import com.example.spring_data_jpa_demo.entity.Resume;
import com.example.spring_data_jpa_demo.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/applicants")
public class ApplicationController {


    ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<Applicant> getAllApplicants() {
        return applicationService.getAllApplicants();
    }

    @GetMapping("/greet")
    public String greeting(){
        return "Hello World";
    }

    @PostMapping("/create")
    public ResponseEntity<Applicant> saveApplicant(@RequestBody Applicant applicant) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(applicationService.saveApplicant(applicant));

    }

    @GetMapping("/page")
    public Iterable<Applicant> getApplicantsWithPagination(@RequestParam int page, @RequestParam int size) {
        return applicationService.getApplicantWithPagination(page, size);
    }

    @GetMapping("/status")
    public List<Applicant> getApplicantsByStatus(@RequestParam String status) {
        return applicationService.getApplicantByStatus(status);
    }

    @PostMapping("/{applicantId}/resume/save")
    public ResponseEntity<Resume> saveResume(@PathVariable Long applicantId, @RequestBody Resume resume) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(applicationService.saveResume(applicantId, resume));
    }

    @PostMapping("/createJob")
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(applicationService.saveJob(job));
    }

    @PostMapping("/{applicantId}/applyJob/{jobId}")
    public ResponseEntity<Applicant> applyJob(@PathVariable Long applicantId,
                                        @PathVariable Long jobId) {
        return ResponseEntity
                .ok(applicationService.applyJob(applicantId, jobId));

    }


}
