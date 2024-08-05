package pt.ulisboa.tecnico.rnl.dei.dms.grants.domain;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.dto.GrantDto;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.DateHandler;
import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.GrantEvaluationCategory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "grants")
public class Grant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long monthlyIncome;

    private Integer vacancy;

    @Column(name = "curricular_evaluation_weight")
    private Double curricularEvaluationWeight;

    @Column(name = "interview_weight")
    private Double interviewWeight;

    @Column(name = "practical_exercise_weight")
    private Double practicalExerciseWeight;

    private boolean onGoing = true;

    private List<Long> grantees = new ArrayList<>();

    private static final double DEFAULT_CURRICULAR_EVALUATION_WEIGHT = 0.4;
    private static final double DEFAULT_INTERVIEW_WEIGHT = 0.4;
    private static final double DEFAULT_PRACTICAL_EXERCISE_WEIGHT = 0.2;

    @OneToMany(mappedBy = "grant", cascade = CascadeType.REMOVE)
    private List<Enrollment> enrollments = new ArrayList<>();

    public Grant() {
    }


    public Grant(GrantDto grantDto) {
        setStartDate(DateHandler.toLocalDateTime(grantDto.getStartDate()));
        setEndDate(DateHandler.toLocalDateTime(grantDto.getEndDate()));
        setMonthlyIncome(grantDto.getMonthlyIncome());
        setVacancy(grantDto.getVacancy());
        setDefaultWeights();

        verifyInvariants();
    }

    public void update(GrantDto grantDto) {

        setStartDate(DateHandler.toLocalDateTime(grantDto.getStartDate()));
        setEndDate(DateHandler.toLocalDateTime(grantDto.getEndDate()));
        setMonthlyIncome(grantDto.getMonthlyIncome());
        setVacancy(grantDto.getVacancy());
        setCurricularEvaluationWeight(grantDto.getCurricularEvaluationWeight());
        setInterviewWeight(grantDto.getInterviewWeight());
        setPracticalExerciseWeight(grantDto.getPracticalExerciseWeight());
        verifyInvariants();
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

    public Integer getVacancy() {
        return vacancy;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
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

    public Double getDefaultCurricularEvaluationWeight() {
        return DEFAULT_CURRICULAR_EVALUATION_WEIGHT;
    }

    public Double getDefaultInterviewWeight() {
        return DEFAULT_INTERVIEW_WEIGHT;
    }

    public Double getDefaultPracticalExerciseWeight() {
        return DEFAULT_PRACTICAL_EXERCISE_WEIGHT;
    }
    
    public List<Long> getGrantees() {
        return grantees;
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

    public void setDefaultWeights() {
        this.curricularEvaluationWeight = DEFAULT_CURRICULAR_EVALUATION_WEIGHT;
        this.interviewWeight = DEFAULT_INTERVIEW_WEIGHT;
        this.practicalExerciseWeight = DEFAULT_PRACTICAL_EXERCISE_WEIGHT;
    }
    
    public void setOnGoing(boolean onGoing) {
        this.onGoing = onGoing;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void setGrantees(List<Long> grantees) {
        this.grantees = grantees;
    }

    public void updateEvaluationWeights(GrantDto grantDto) {
        setCurricularEvaluationWeight(grantDto.getCurricularEvaluationWeight());
        setInterviewWeight(grantDto.getInterviewWeight());
        setPracticalExerciseWeight(grantDto.getPracticalExerciseWeight());
        areWeightsValid();
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
                '}';
    }

    private void verifyInvariants() {
        isValidIncome();
        isStartDateValid();
        isEndDateValid();
        isGrantDurationValid();
        isVancancyValid();
        areWeightsValid();
    }

    public void isValidIncome() {
        if(this.monthlyIncome == null) {
            throw new CMSException(ErrorMessage.GRANT_MONTHLY_INCOME_CANNOT_BE_EMPTY);
        }

        if(this.monthlyIncome < 0) {
            throw new CMSException(ErrorMessage.GRANT_MONTHLY_INCOME_CANNOT_BE_NEGATIVE);
        }
    }

    public void isStartDateValid() {
        if(this.startDate == null) {
            throw new CMSException(ErrorMessage.GRANT_START_DATE_CANNOT_BE_EMPTY);
        }
    }

    public void isEndDateValid() {
        if(this.endDate == null) {
            throw new CMSException(ErrorMessage.GRANT_END_DATE_CANNOT_BE_EMPTY);
        }
    }

    public void isGrantDurationValid() {
        if(this.startDate.isAfter(this.endDate)) {
            throw new CMSException(ErrorMessage.GRANT_START_DATE_CANNOT_BE_AFTER_END_DATE);
        }
    }

    public void isVancancyValid() {
        if(this.vacancy == null || this.vacancy == 0) {
            throw new CMSException(ErrorMessage.GRANT_VACANCY_CANNOT_BE_EMPTY);
        }

        if(this.vacancy < 0) {
            throw new CMSException(ErrorMessage.GRANT_VACANCY_CANNOT_BE_NEGATIVE);
        }
    }

    public void areWeightsValid() {
        if(this.curricularEvaluationWeight + this.interviewWeight + this.practicalExerciseWeight != 1) {
            throw new CMSException(ErrorMessage.CATEGORIES_WEIGHT_MUST_SUM_TO_ONE);
        }
    }
}
