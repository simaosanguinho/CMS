package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;


import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.domain.Grant;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.domain.Evaluation;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "grant_id")
    private Grant grant;

    @OneToOne(mappedBy = "enrollment", cascade = CascadeType.REMOVE)
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation; 

    private boolean isEvaluated = false;
    private Double finalScore = 0.0;

    public Enrollment() {
    }

    public Enrollment(Candidate candidate, Grant grant, EnrollmentDto enrollmentDto) {
        setCandidate(candidate);
        setGrant(grant);
        setIsEvaluated(enrollmentDto.isEvaluated());

        verifyInvariants();
    }
    public Long getId() {
        return id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public Grant getGrant() {
        return grant;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public void setGrant(Grant grant) {
        this.grant = grant;
    }

    public boolean isEvaluated() {
        return isEvaluated;
    }

    public void setIsEvaluated(boolean isEvaluated) {
        this.isEvaluated = isEvaluated;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    public void verifyInvariants() {
        if(candidate == null) {
            throw new CMSException(ErrorMessage.ENROLLMENT_CANDIDATE_CANNOT_BE_EMPTY);
        }
        if(grant == null) {
            throw new CMSException(ErrorMessage.ENROLLMENT_GRANT_CANNOT_BE_EMPTY);
        }

        if(finalScore > 20 || finalScore < 0) {
            throw new CMSException(ErrorMessage.ENROLLMENT_FINAL_SCORE_INVALID);
        }
    }

}
