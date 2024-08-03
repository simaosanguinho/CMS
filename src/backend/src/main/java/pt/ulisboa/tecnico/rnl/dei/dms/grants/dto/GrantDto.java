package pt.ulisboa.tecnico.rnl.dei.dms.grants.dto;

import java.util.Map;

import pt.ulisboa.tecnico.rnl.dei.dms.grants.domain.Grant;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.DateHandler;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.GrantEvaluationMethods;


public class GrantDto {
    private Long id;
    private String startDate;
    private String endDate;
    private Long monthlyIncome;
    private Integer vacancy;
    private Double curricularEvaluationWeight;
    private Double interviewWeight;
    private Double practicalExerciseWeight;

    public GrantDto() {
    }

    public GrantDto(Grant grant) {
        setId(grant.getId());
        setStartDate(DateHandler.toISOString(grant.getStartDate()));
        setEndDate(DateHandler.toISOString(grant.getEndDate()));
        setMonthlyIncome(grant.getMonthlyIncome());
        setVacancy(grant.getVacancy());
        setEvaluationWeights(grant.getWeights());

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

    public Map<GrantEvaluationMethods, Double> getEvaluationWeights() {
        return Map.of(
                GrantEvaluationMethods.CURRICULAR_EVALUATION, curricularEvaluationWeight,
                GrantEvaluationMethods.INTERVIEW, interviewWeight,
                GrantEvaluationMethods.PRACTICAL_EXERCISE, practicalExerciseWeight
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

    public void setEvaluationWeights(Map<GrantEvaluationMethods, Double> weights) {
        this.curricularEvaluationWeight = weights.get(GrantEvaluationMethods.CURRICULAR_EVALUATION);
        this.interviewWeight = weights.get(GrantEvaluationMethods.INTERVIEW);
        this.practicalExerciseWeight = weights.get(GrantEvaluationMethods.PRACTICAL_EXERCISE);
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
