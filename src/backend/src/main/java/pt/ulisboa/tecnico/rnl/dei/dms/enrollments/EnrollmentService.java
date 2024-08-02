package pt.ulisboa.tecnico.rnl.dei.dms.enrollments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.domain.Grant;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository.EnrollmentRepository;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.repository.CandidateRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.repository.GrantRepository;

import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.CMSException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private GrantRepository grantRepository;

    public EnrollmentDto createEnrollment(Long candidateId, Long grantId, EnrollmentDto enrollmentDto) {
        
        if(enrollmentDto == null) {
            throw new CMSException(ErrorMessage.ENROLLMENT_CANNOT_BE_EMPTY);
        }

        if(candidateId == null) {
            throw new CMSException(ErrorMessage.ENROLLMENT_CANDIDATE_CANNOT_BE_EMPTY);
        }
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() -> new CMSException(ErrorMessage. CANDIDATE_NOT_FOUND));

        if(grantId == null) {
            throw new CMSException(ErrorMessage.ENROLLMENT_GRANT_CANNOT_BE_EMPTY);
        }
        Grant grant = grantRepository.findById(grantId).orElseThrow(() -> new CMSException(ErrorMessage.GRANT_NOT_FOUND));

        Enrollment enrollment = new Enrollment(candidate, grant, enrollmentDto);
        enrollmentRepository.save(enrollment);
        return new EnrollmentDto(enrollment);

    }

    public List<EnrollmentDto> getEnrollmentsByGrantId(Long grantId) {
        return enrollmentRepository.findByGrantId(grantId).stream().map(EnrollmentDto::new).collect(Collectors.toList());
    }

    public List<EnrollmentDto> getEnrollmentsByCandidateId(Long candidateId) {
        return enrollmentRepository.findByCandidateId(candidateId).stream().map(EnrollmentDto::new).collect(Collectors.toList());
    }
}
