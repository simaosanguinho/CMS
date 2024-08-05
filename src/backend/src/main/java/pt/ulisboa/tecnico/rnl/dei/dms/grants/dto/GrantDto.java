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
    private boolean onGoing;
    private Integer numberOfEnrollments;

    public GrantDto() {
    }

    public GrantDto(Grant grant) {
        setId(grant.getId());
        setStartDate(DateHandler.toISOString(grant.getStartDate()));
        setEndDate(DateHandler.toISOString(grant.getEndDate()));
        setMonthlyIncome(grant.getMonthlyIncome());
        setVacancy(grant.getVacancy());
        setCurricularEvaluationWeight(grant.getCurricularEvaluationWeight());
        setInterviewWeight(grant.getInterviewWeight());
        setPracticalExerciseWeight(grant.getPracticalExerciseWeight());

        setOnGoing(grant.isOnGoing());
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

    public Integer getVacancy() {
        return vacancy;
    }

    public Integer getNumberOfEnrollments() {
        return numberOfEnrollments;
    }

    public boolean isOnGoing() {
        return onGoing;
    }

    public Double getCurricularEvaluationWeight() {
        return curricularEvaluationWeight;
    }

    public Double getInterviewWeight() {
        return interviewWeight;
    }

    public Double getPracticalExerciseWeight() {
        return practicalExerciseWeight;
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

    public void setCurricularEvaluationWeight(Double curricularEvaluationWeight) {
        this.curricularEvaluationWeight = curricularEvaluationWeight;
    }

    public void setInterviewWeight(Double interviewWeight) {
        this.interviewWeight = interviewWeight;
    }

    public void setPracticalExerciseWeight(Double practicalExerciseWeight) {
        this.practicalExerciseWeight = practicalExerciseWeight;
    }

    public void setOnGoing(boolean onGoing) {
        this.onGoing = onGoing;
    }

    public void setNumberOfEnrollments(Integer numberOfEnrollments) {
        this.numberOfEnrollments = numberOfEnrollments;
    }

    @Override
    public String toString() {
        return "Grant{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", monthlyIncome=" + monthlyIncome +
                ", vacancy=" + vacancy +
                ", curricularEvaluationWeight=" + curricularEvaluationWeight +
                ", interviewWeight=" + interviewWeight +
                ", practicalExerciseWeight=" + practicalExerciseWeight +
                ", onGoing=" + onGoing +
                ", numberOfEnrollments=" + numberOfEnrollments +
                '}';
    }

}
