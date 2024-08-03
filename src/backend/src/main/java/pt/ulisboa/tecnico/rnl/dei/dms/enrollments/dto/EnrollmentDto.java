package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;

public class EnrollmentDto {
    private Long id;
    private Long candidateId;
    private Long grantId;

    public EnrollmentDto() {
    }

    public EnrollmentDto(Enrollment enrollment) {
        setId(enrollment.getId());
        setGrantId(enrollment.getGrant().getId());
        setCandidateId(enrollment.getCandidate().getId());
    }

    public Long getId() {
        return id;
    }

    public Long getGrantId() {
        return grantId;
    }

    public Long getCandidate() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGrantId(Long grantId) {
        this.grantId = grantId;
    }

}
