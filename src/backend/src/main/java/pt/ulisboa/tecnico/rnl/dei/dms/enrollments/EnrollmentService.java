package pt.ulisboa.tecnico.rnl.dei.dms.enrollments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.dto.GrantDto;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.repository.CandidateRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.repository.GrantRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.domain.Grant;

import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.CMSException;

import java.util.Comparator;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private GrantRepository grantRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public EnrollmentDto createEnrollment(Long candidateId, Long grantId, EnrollmentDto enrollmentDto) {

        if (enrollmentDto == null) {
            throw new CMSException(ErrorMessage.ENROLLMENT_CANNOT_BE_EMPTY);
        }

        if (candidateId == null) {
            throw new CMSException(ErrorMessage.ENROLLMENT_CANDIDATE_CANNOT_BE_EMPTY);
        }
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CMSException(ErrorMessage.CANDIDATE_NOT_FOUND));

        if (grantId == null) {
            throw new CMSException(ErrorMessage.ENROLLMENT_GRANT_CANNOT_BE_EMPTY);
        }
        Grant grant = grantRepository.findById(grantId)
                .orElseThrow(() -> new CMSException(ErrorMessage.GRANT_NOT_FOUND));

        Enrollment enrollment = new Enrollment(candidate, grant, enrollmentDto);
        enrollmentRepository.save(enrollment);
        return new EnrollmentDto(enrollment);

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<EnrollmentDto> getEnrollmentsByGrantId(Long grantId) {
        if (grantId == null) {
            throw new CMSException(ErrorMessage.GRANT_NOT_FOUND);
        }
        return enrollmentRepository.findByGrantId(grantId).stream()
                .sorted(Comparator.comparing(Enrollment::getId))
                .map(EnrollmentDto::new)
                .toList();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<EnrollmentDto> getEnrollmentsByCandidateId(Long candidateId) {
        if (candidateId == null) {
            throw new CMSException(ErrorMessage.CANDIDATE_NOT_FOUND);
        }
        return enrollmentRepository.findByCandidateId(candidateId).stream()
                .sorted(Comparator.comparing(Enrollment::getId))
                .map(EnrollmentDto::new)
                .toList();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteEnrollment(Long enrollmentId) {
        if (enrollmentId == null) {
            throw new CMSException(ErrorMessage.ENROLLMENT_NOT_FOUND);
        }
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new CMSException(ErrorMessage.ENROLLMENT_NOT_FOUND));
        enrollmentRepository.delete(enrollment);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<CandidateDto> getUnenrolledCandidates(Long grantId) {
        if (grantId == null) {
            throw new CMSException(ErrorMessage.GRANT_NOT_FOUND);
        }

        Grant grant = grantRepository.findById(grantId)
                .orElseThrow(() -> new CMSException(ErrorMessage.GRANT_NOT_FOUND));

        return candidateRepository.findAll().stream()
                .filter(candidate -> !enrollmentRepository.existsByCandidateAndGrant(candidate, grant))
                .sorted(Comparator.comparing(Candidate::getId))
                .map(CandidateDto::new)
                .toList();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<GrantDto> getEnrolledGrantsByCandidateId(Long candidateId) {
        if (candidateId == null) {
            throw new CMSException(ErrorMessage.CANDIDATE_NOT_FOUND);
        }

        List<Enrollment> enrollments = enrollmentRepository.findByCandidateId(candidateId);
        System.out.println("Grants: " + enrollments.stream()
                .map(Enrollment::getGrant)
                .toList());
        // get grants from enrollments and return them
        return enrollments.stream()
                .map(Enrollment::getGrant)
                .sorted(Comparator.comparing(Grant::getId))
                .map(GrantDto::new)
                .toList();
    }

}
