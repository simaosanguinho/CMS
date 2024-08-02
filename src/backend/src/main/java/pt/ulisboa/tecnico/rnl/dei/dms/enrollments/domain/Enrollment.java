package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;


import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.domain.Grant;
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

    public Enrollment() {
    }

    public Enrollment(Candidate candidate, Grant grant) {
        this.candidate = candidate;
        this.grant = grant;
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

    
}
