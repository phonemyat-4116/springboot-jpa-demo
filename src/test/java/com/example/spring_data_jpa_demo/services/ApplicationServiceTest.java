package com.example.spring_data_jpa_demo.services;

import com.example.spring_data_jpa_demo.entity.Applicant;
import com.example.spring_data_jpa_demo.entity.Job;
import com.example.spring_data_jpa_demo.entity.Resume;
import com.example.spring_data_jpa_demo.exceptions.ApplicantNotFoundException;
import com.example.spring_data_jpa_demo.repositories.ApplicantCrudRepository;
import com.example.spring_data_jpa_demo.repositories.ApplicantJpaRepository;
import com.example.spring_data_jpa_demo.repositories.JobJpaRepository;
import com.example.spring_data_jpa_demo.repositories.ResumeJpaRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    ApplicantCrudRepository applicantCrudRepository;
    @Mock
    ApplicantJpaRepository applicantJpaRepository;
    @Mock
    ResumeJpaRepository resumeJpaRepository;
    @Mock
    JobJpaRepository jobJpaRepository;


    @InjectMocks // Inject above mocks into this
    ApplicationService applicationService;

    Applicant applicant1;
    Applicant applicant2;

    @BeforeAll
    public static void init(){
        System.out.println("Before All");
    }

    @BeforeEach
    public void initEachTest(){
        System.out.println("Before Each");

        // Arrange
        applicant1 = new Applicant();
        applicant1.setId(1L);
        applicant1.setName("John");
        applicant1.setEmail("john@email.com");
        applicant1.setPhone("094859875");
        applicant1.setStatus("Active");

        applicant2 = new Applicant();
        applicant2.setId(2L);
        applicant2.setName("Eric");
        applicant2.setEmail("eric@email.com");
        applicant2.setPhone("094559875");
        applicant2.setStatus("InActive");
    }

    @Test
    void saveApplicantTest() {

        Applicant applicant = new Applicant();
        applicant.setId(1L);
        applicant.setName("John");
        applicant.setEmail("john@email.com");
        applicant.setPhone("094859875");
        applicant.setStatus("Active");

        // we can omit "Mockito" if we have import properly
        when(applicantCrudRepository.save(applicant)).thenReturn(applicant);

        Applicant savedApplicant = applicationService.saveApplicant(applicant);

        assertDoesNotThrow(() -> savedApplicant);
        assertNotNull(savedApplicant);
        assertEquals(applicant, savedApplicant);
        assertEquals(1L, (long) applicant.getId());

        System.out.println("this is insane");
    }

    @Test
    void deleteApplicant_success(){
        // Arrange
        Long id = 1L;
        when(applicantJpaRepository.existsById(id)).thenReturn(true);

        // Act
        applicationService.deleteById(id);

        // then
        /**
         * It must call repository.deleteById(id)
         * Exactly once
         * With the correct id
         *
         * No assert used because the method is void, not return.
         */
        // Assert
        verify(applicantJpaRepository, times(1)).deleteById(id);

    }

    @Test
    void deleteApplicantNotFound(){

        Long id = 1L;

        when(applicantJpaRepository.existsById(id)).thenReturn(false);

        // If applicant does not exist → throw exception
        assertThrows(ApplicantNotFoundException.class, () -> applicationService.deleteById(id));

        // AND do not delete anything
        verify(applicantJpaRepository, never()).deleteById(id);
    }

    @Test
    void getApplicantByStatusTest(){
        // Arrange
        String status = "Active";

        List<Applicant> mockApplicants = List.of(applicant1, applicant2);
        when(applicantJpaRepository.findByStatusOrderByNameAsc(status)).thenReturn(mockApplicants);

        // Act
        List<Applicant> applicants = applicationService.getApplicantByStatus(status);

        // Assert
        assertNotNull(applicants);
        assertEquals(mockApplicants.getFirst().getName(), applicants.getFirst().getName());


        // verify (additional step)
        verify(applicantJpaRepository, times(1)).findByStatusOrderByNameAsc(status);


    }

    @Test
    void saveResumeTest(){

        // Arrage
        Long applicantId = 1L;

        Resume resume = new Resume();
        resume.setContent("Good Personal Skill");


        // ✅ MOCK applicant lookup
        when(applicantJpaRepository.findById(applicantId)).thenReturn(Optional.of(applicant1));

        // ✅ MOCK resume save
        // Resume.class -> matcher (not real object)
        when(resumeJpaRepository.save(any(Resume.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Resume savedResume = applicationService.saveResume(applicantId, resume);

        // Assert
        assertNotNull(savedResume);
        assertEquals(applicantId, savedResume.getApplicant().getId());

        // Verify
        verify(applicantJpaRepository, times(1)).findById(applicantId);
        verify(resumeJpaRepository, times(1)).save(any(Resume.class));

    }

    @Test
    void saveJobTest(){

        // Arrange
        Job job = new Job();
        job.setId(1L);
        job.setTitle("Customer Service Support");
        job.setDepartment("Admin");
        when(jobJpaRepository.save(job)).thenReturn(job);

        // Act
        Job savedJob = applicationService.saveJob(job);

        // Assert
        assertNotNull(savedJob);
        assertEquals(job, savedJob);

        // Verify
        verify(jobJpaRepository, times(1)).save(job);

    }

    @Test
    void applyJobTest(){

        // Arrange
        Long applicantId = 1L;

        Job job = new Job();
        Long jobId = 1L;
        job.setTitle("Customer Service Support");
        job.setDepartment("Admin");

        when(applicantJpaRepository.findById(applicantId)).thenReturn(Optional.of(applicant1));
        when(jobJpaRepository.findById(jobId)).thenReturn(Optional.of(job));

        when(applicantJpaRepository.save(any(Applicant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Applicant appliedJob = applicationService.applyJob(applicantId, jobId);

        // Assert
        assertNotNull(appliedJob);
        assertNotNull(appliedJob.getJobs());
        assertEquals(1, appliedJob.getJobs().size());
        assertTrue(appliedJob.getJobs().contains(job));

        //Verify
        verify(applicantJpaRepository, times(1)).findById(applicantId);
        verify(jobJpaRepository, times(1)).findById(jobId);
        verify(applicantJpaRepository, times(1)).save(any(Applicant.class));


    }

    @Test
    void getApplicantTest() {
        System.out.println("get Applicant test");
    }

    @AfterEach
    public void destroy(){
        System.out.println("After Each");
    }

    @AfterAll
    public static void cleanup(){
        System.out.println("After All");
    }
}

/**
 * Service resume ── set applicant ── save()
 *                                │
 * Mockito returns same instance ◄─┘
 * Test sees mutation ✔
 *
 *
 * You do NOT check if the test setup set the applicant.
 * You check if the SERVICE set the applicant
 *
 * Think of this as:
 * save(📦
 * any(Resume.class) = “any box”
 *
 * You don’t see what’s inside the box
 * At runtime, the box contains applicant
 * thenAnswer() opens the box and gives it back to you
 */