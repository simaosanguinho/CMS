package pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;

public class CandidateDto {
    private Long id;
    private String name;
    private String email;
    private String istID;
    private Integer numberOfEnrollments;

    public CandidateDto() {
    }

    public CandidateDto(Candidate candidate) {
        setId(candidate.getId());
        setIstID(candidate.getIstID());
        setEmail(candidate.getEmail());
        setName(candidate.getName());
        setNumberOfEnrollments(candidate.getEnrollments().size());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getIstID() {
        return istID;
    }

    public Integer getNumberOfEnrollments() {
        return numberOfEnrollments;
    }

    public void setNumberOfEnrollments(Integer numberOfEnrollments) {
        this.numberOfEnrollments = numberOfEnrollments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIstID(String istID) {
        this.istID = istID;
    }

    @Override
    public String toString() {
        return "CandidateDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", istID='" + istID + '\'' +
                ", numberOfEnrollments=" + numberOfEnrollments +
                '}';
    }
}
