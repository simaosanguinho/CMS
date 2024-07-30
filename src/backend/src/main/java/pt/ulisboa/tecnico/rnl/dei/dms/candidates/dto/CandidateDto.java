package pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;


public class CandidateDto {
    private Long id;
    private String name;
    private String email;
    private String istID;

    public CandidateDto() {
    }

    public CandidateDto(String name, String email, String istID) {
        this.name = name;
        this.email = email;
        this.istID = istID;
    }

    public CandidateDto(Candidate candidate) {
        this.id = candidate.getId();
        this.name = candidate.getName();
        this.email = candidate.getEmail();
        this.istID = candidate.getIstID();
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
                '}';
    }
}
