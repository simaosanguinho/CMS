package pt.ulisboa.tecnico.rnl.dei.dms.evaluations.dto;

import java.util.EnumMap;
import java.util.Map;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.domain.Evaluation;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.GrantEvaluationCategory;

public class EvaluationDto {
    private Long id;

    private Map<GrantEvaluationCategory, Double> scores = new EnumMap<>(GrantEvaluationCategory.class);

    private Long enrollmentId;



    public EvaluationDto() {
        setDefaultScoreValues();
    }

    public EvaluationDto(Evaluation evaluation) {
        setId(evaluation.getId());
        setEnrollmentId(evaluation.getEnrollment().getId());
        setScores(evaluation.getScores());
    }

    public Long getId() {
        return id;
    }


    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public Map<GrantEvaluationCategory, Double> getScores() {
        return scores;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScores(Map<GrantEvaluationCategory, Double> scores) {
        this.scores = scores;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    private void setDefaultScoreValues() {

    }

}
