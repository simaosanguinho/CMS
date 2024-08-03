package pt.ulisboa.tecnico.rnl.dei.dms.enrollments;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.dto.GrantDto;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@RestController()
@RequestMapping
public class EnrollmentController {
    
    @Autowired
    EnrollmentService enrollmentService;

    @PostMapping("/grants/{grantId}/enrollments/{candidateId}")
    public EnrollmentDto createEnrollment(@PathVariable Long candidateId,@PathVariable Long grantId, @RequestBody EnrollmentDto enrollmentDto) {
        return enrollmentService.createEnrollment(candidateId, grantId, enrollmentDto);
    }

    @GetMapping("/grants/{grantId}/enrollments")
    public List<EnrollmentDto> getEnrollmentsByGrantId(@PathVariable Long grantId) {
        return enrollmentService.getEnrollmentsByGrantId(grantId);
    }

    @GetMapping("/candidates/enrollments/{candidateId}")
    public List<EnrollmentDto> getEnrollmentsByCandidateId(@PathVariable Long candidateId) {
        return enrollmentService.getEnrollmentsByCandidateId(candidateId);
    }

    @DeleteMapping("/grants/enrollments/{enrollmentId}")
    public void deleteEnrollment(@PathVariable Long enrollmentId) {
        enrollmentService.deleteEnrollment(enrollmentId);
    }

    @GetMapping("/grants/{grantId}/unenrolled")
    public List<CandidateDto> getUnenrolledCandidates(@PathVariable Long grantId) {
        return enrollmentService.getUnenrolledCandidates(grantId);
    }

    @GetMapping("/grants/{candidateId}/enrolled")
    public List<GrantDto> getEnrolledGrantsByCandidateId(@PathVariable Long candidateId) {
        System.out.println("candidateId: " + candidateId);
        return enrollmentService.getEnrolledGrantsByCandidateId(candidateId);
    }
}
