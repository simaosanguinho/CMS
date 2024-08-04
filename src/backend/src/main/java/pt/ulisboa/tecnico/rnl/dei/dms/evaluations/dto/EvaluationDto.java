package pt.ulisboa.tecnico.rnl.dei.dms.evaluations.dto;

import java.util.EnumMap;
import java.util.Map;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.domain.Evaluation;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.GrantEvaluationCategory;
import java.util.List;
public class EvaluationDto {
    private Long id;

    private List<Double> scores;

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
        // build a map with the scores
        Map<GrantEvaluationCategory, Double> scoresMap = new EnumMap<>(GrantEvaluationCategory.class);
        for (int i = 0; i < GrantEvaluationCategory.values().length; i++) {
            scoresMap.put(GrantEvaluationCategory.values()[i], scores.get(i));
        }
        return scoresMap;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScores(List<Double> scores) {
        this.scores = scores;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    private void setDefaultScoreValues() {

    }

}
