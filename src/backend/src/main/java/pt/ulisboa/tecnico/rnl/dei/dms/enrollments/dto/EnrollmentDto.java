package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;

public class EnrollmentDto {
    private Long id;
    private CandidateDto candidateDto;
    private Long grantId;

    public EnrollmentDto() {
    }

    public EnrollmentDto(Enrollment enrollment) {
        setId(enrollment.getId());
        setGrantId(enrollment.getGrant().getId());
        setCandidate(new CandidateDto(enrollment.getCandidate()));
    }

    public Long getId() {
        return id;
    }

    public Long getGrantId() {
        return grantId;
    }

    public CandidateDto getCandidate() {
        return candidateDto;
    }

    public void setCandidate(CandidateDto candidateDto) {
        this.candidateDto = candidateDto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGrantId(Long grantId) {
        this.grantId = grantId;
    }

}
