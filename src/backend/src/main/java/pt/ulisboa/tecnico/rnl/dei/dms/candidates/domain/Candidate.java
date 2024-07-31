package pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String istID;

    public Candidate() {
    }

    public Candidate(String name, String email, String istID) {
        this.name = name;
        this.email = email;
        this.istID = istID;
    }

    public Candidate(CandidateDto candidateDto) {
        this.name = candidateDto.getName();
        this.email = candidateDto.getEmail();
        this.istID = candidateDto.getIstID();
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
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", istID='" + istID + '\'' +
                '}';
    }

}
