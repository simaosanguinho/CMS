package pt.ulisboa.tecnico.rnl.dei.dms.grants.dto;

import java.util.Set;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.domain.Grant;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.DateHandler;


public class GrantDto {
    private Long id;
    private String startDate;
    private String endDate;
    private Long monthlyIncome;
    private Set <CandidateDto> candidateDto;
    private Integer vacancy;

    public GrantDto() {
    }

    public GrantDto(Grant grant) {
        this.id = grant.getId();
        this.startDate = DateHandler.toISOString(grant.getStartDate());
        this.endDate = DateHandler.toISOString(grant.getEndDate());
        this.monthlyIncome = grant.getMonthlyIncome();
        this.vacancy = grant.getVacancy();
    }

    public GrantDto(Grant grant, boolean deepCopyCandidates) {
        this.id = grant.getId();
        this.startDate = DateHandler.toISOString(grant.getStartDate());
        this.endDate = DateHandler.toISOString(grant.getEndDate());
        this.monthlyIncome = grant.getMonthlyIncome();
        this.vacancy = grant.getVacancy();
        
        if(deepCopyCandidates) {
            this.candidateDto = grant.getCandidates().stream()
                                .map(candidate -> new CandidateDto(candidate))
                                .collect(Collectors.toSet());
        }
    }

    public Long getId() {
        return id;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Long getMonthlyIncome() {
        return monthlyIncome;
    }

    public Set<CandidateDto> getCandidateDto() {
        return candidateDto;
    }

    public Integer getVacancy() {
        return vacancy;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setMonthlyIncome(Long monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public void setCandidateDto(Set<CandidateDto> candidateDto) {
        this.candidateDto = candidateDto;
    }

    public void setVacancy(Integer vacancy) {
        this.vacancy = vacancy;
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
