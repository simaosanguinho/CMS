package pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto;

import java.util.Set;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.dto.GrantDto;
import java.util.stream.Collectors;

public class CandidateDto {
    private Long id;
    private String name;
    private String email;
    private String istID;
    private Set <GrantDto> grantDto;

    public CandidateDto() {
    }

    public CandidateDto(Candidate candidate) {
        this.id = candidate.getId();
        this.name = candidate.getName();
        this.email = candidate.getEmail();
        this.istID = candidate.getIstID();
    }

    public CandidateDto(Candidate candidate, boolean deepCopyGrants) {
        this.id = candidate.getId();
        this.name = candidate.getName();
        this.email = candidate.getEmail();
        this.istID = candidate.getIstID();
        
        if(deepCopyGrants) {
            this.grantDto = candidate.getGrants().stream()
                                .map(grant -> new GrantDto(grant))
                                .collect(Collectors.toSet());
        }
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
