package pt.ulisboa.tecnico.rnl.dei.dms.evaluations.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.domain.Evaluation;

public class EvaluationDto {
    private Long id;

    // Parameters of Evaluation
    private Double curricularEvaluation;
    private Double interview;
    private Double practicalExercise;

    private Long enrollmentId;



    public EvaluationDto() {
    }

    public EvaluationDto(Evaluation evaluation) {
        setId(evaluation.getId());
        setCurricularEvaluation(evaluation.getCurricularEvaluation());
        setInterview(evaluation.getInterview());
        setPracticalExercise(evaluation.getPracticalExercise());
        setEnrollmentId(evaluation.getEnrollment().getId());
    }

    public Long getId() {
        return id;
    }

    public Double getCurricularEvaluation() {
        return curricularEvaluation;
    }

    public Double getInterview() {
        return interview;
    }

    public Double getPracticalExercise() {
        return practicalExercise;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCurricularEvaluation(Double curricularEvaluation) {
        this.curricularEvaluation = curricularEvaluation;
    }

    public void setInterview(Double interview) {
        this.interview = interview;
    }

    public void setPracticalExercise(Double practicalExercise) {
        this.practicalExercise = practicalExercise;
    }
    
    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

}
