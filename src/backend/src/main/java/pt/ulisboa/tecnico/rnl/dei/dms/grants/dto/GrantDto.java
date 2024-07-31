package pt.ulisboa.tecnico.rnl.dei.dms.grants.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.domain.Grant;


public class GrantDto {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long monthlyIncome;
    private Set <CandidateDto> candidateDto;

    public GrantDto() {
    }

    public GrantDto(Grant grant) {
        this.id = grant.getId();
        this.startDate = grant.getStartDate();
        this.endDate = grant.getEndDate();
        this.monthlyIncome = grant.getMonthlyIncome();
    }

    public GrantDto(Grant grant, boolean deepCopyCandidates) {
        this.id = grant.getId();
        this.startDate = grant.getStartDate();
        this.endDate = grant.getEndDate();
        this.monthlyIncome = grant.getMonthlyIncome();
        
        if(deepCopyCandidates) {
            this.candidateDto = grant.getCandidates().stream()
                                .map(candidate -> new CandidateDto(candidate))
                                .collect(Collectors.toSet());
        }
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getMonthlyIncome() {
        return monthlyIncome;
    }

    public Set<CandidateDto> getCandidateDto() {
        return candidateDto;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setMonthlyIncome(Long monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public void setCandidateDto(Set<CandidateDto> candidateDto) {
        this.candidateDto = candidateDto;
    }

    @Override
    public String toString() {
        return "Grant{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", monthlyIncome=" + monthlyIncome +
                '}';
    }

}
