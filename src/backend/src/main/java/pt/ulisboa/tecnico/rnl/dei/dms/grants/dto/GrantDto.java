package pt.ulisboa.tecnico.rnl.dei.dms.grants.dto;

import java.util.Map;

import pt.ulisboa.tecnico.rnl.dei.dms.grants.domain.Grant;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.DateHandler;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.GrantEvaluationCategory;


public class GrantDto {
    private Long id;
    private String startDate;
    private String endDate;
    private Long monthlyIncome;
    private Integer vacancy;
    private Double curricularEvaluationWeight;
    private Double interviewWeight;
    private Double practicalExerciseWeight;
    private boolean ongoing;

    public GrantDto() {
    }

    public GrantDto(Grant grant) {
        setId(grant.getId());
        setStartDate(DateHandler.toISOString(grant.getStartDate()));
        setEndDate(DateHandler.toISOString(grant.getEndDate()));
        setMonthlyIncome(grant.getMonthlyIncome());
        setVacancy(grant.getVacancy());
        setEvaluationWeights(grant.getWeights());
        setOngoing(grant.isOngoing());

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


    public Integer getVacancy() {
        return vacancy;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public Map<GrantEvaluationCategory, Double> getEvaluationWeights() {
        return Map.of(
                GrantEvaluationCategory.CURRICULAR_EVALUATION, curricularEvaluationWeight,
                GrantEvaluationCategory.INTERVIEW, interviewWeight,
                GrantEvaluationCategory.PRACTICAL_EXERCISE, practicalExerciseWeight
        );
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

    public void setVacancy(Integer vacancy) {
        this.vacancy = vacancy;
    }

    public void setEvaluationWeights(Map<GrantEvaluationCategory, Double> weights) {
        this.curricularEvaluationWeight = weights.get(GrantEvaluationCategory.CURRICULAR_EVALUATION);
        this.interviewWeight = weights.get(GrantEvaluationCategory.INTERVIEW);
        this.practicalExerciseWeight = weights.get(GrantEvaluationCategory.PRACTICAL_EXERCISE);
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
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
