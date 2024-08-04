package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;

public class EnrollmentDto {
    private Long id;
    private CandidateDto candidate;
    private Long grantId;
    private boolean isEvaluated;
    private Double finalScore;
    

    public EnrollmentDto() {
    }

    public EnrollmentDto(Enrollment enrollment) {
        setId(enrollment.getId());
        setGrantId(enrollment.getGrant().getId());
        setCandidate(new CandidateDto(enrollment.getCandidate()));
        setIsEvaluated(enrollment.isEvaluated());
        setFinalScore(enrollment.getFinalScore());
        
    }

    public Long getId() {
        return id;
    }

    public Long getGrantId() {
        return grantId;
    }

    public CandidateDto getCandidate() {
        return candidate;
    }

    public boolean isEvaluated() {
        return isEvaluated;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setCandidate(CandidateDto candidate) {
        this.candidate = candidate;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    public void setGrantId(Long grantId) {
        this.grantId = grantId;
    }

    public void setIsEvaluated(boolean isEvaluated) {
        this.isEvaluated = isEvaluated;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

}
