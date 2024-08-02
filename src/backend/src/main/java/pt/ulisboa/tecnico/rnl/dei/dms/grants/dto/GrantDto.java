package pt.ulisboa.tecnico.rnl.dei.dms.grants.dto;

import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.domain.Grant;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.DateHandler;


public class GrantDto {
    private Long id;
    private String startDate;
    private String endDate;
    private Long monthlyIncome;
    private Integer vacancy;
    private Integer numberOfEnrollments;

    public GrantDto() {
    }

    public GrantDto(Grant grant) {
        setId(grant.getId());
        setStartDate(DateHandler.toISOString(grant.getStartDate()));
        setEndDate(DateHandler.toISOString(grant.getEndDate()));
        setMonthlyIncome(grant.getMonthlyIncome());
        setVacancy(grant.getVacancy());
        setNumberOfEnrollments(grant.getEnrollments().size());

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

    public Integer getNumberOfEnrollments() {
        return numberOfEnrollments;
    }


    public Integer getVacancy() {
        return vacancy;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setNumberOfEnrollments(Integer numberOfEnrollments) {
        this.numberOfEnrollments = numberOfEnrollments;
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
