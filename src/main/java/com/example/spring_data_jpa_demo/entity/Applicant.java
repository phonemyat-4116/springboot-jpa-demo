package com.example.spring_data_jpa_demo.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String status;

    /*
    @OneToOne(mappedBy = "applicant",
            cascade = CascadeType.ALL
            fetch = FetchType.LAZY)
    private Resume resume;

     */

    @ManyToMany
    @JoinTable(
            name = "applicant_jobs",
            joinColumns = @JoinColumn(name = "applicant_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private Set<Job> jobs = new HashSet<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public void applyJob(Job job) {
        this.jobs.add(job);
    }
}

/**
 * When using @GeneratedValue(strategy = GenerationType.SEQUENCE) or .AUTO
 * - Hibernate pre-fetches IDs (50 by default)
 * - Can batch inserts efficiently
 * - Much faster 🚀
 *
 * Using AUTO	✅ Works, but ID jumps
 * Using SEQUENCE	✅ Best for batching
 */
