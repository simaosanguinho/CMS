package pt.ulisboa.tecnico.rnl.dei.dms.evaluations.domain;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.dto.EvaluationDto;
import java.util.EnumMap;
import java.util.Map;
import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.GrantEvaluationCategory;

@Entity
@Table(name = "evaluations")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "evaluation_scores")
    private Map<GrantEvaluationCategory, Double> scores = new EnumMap<>(GrantEvaluationCategory.class);

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    public Evaluation() {
    }

    public Evaluation(EvaluationDto evaluationDto) {
        setCurricularEvaluation(evaluationDto.getCurricularEvaluation());
        setInterview(evaluationDto.getInterview());
        setPracticalExercise(evaluationDto.getPracticalExercise());

        verifyInvariants();
    }

    public void update(EvaluationDto evaluationDto) {
        setCurricularEvaluation(evaluationDto.getCurricularEvaluation());
        setInterview(evaluationDto.getInterview());
        setPracticalExercise(evaluationDto.getPracticalExercise());
    }

    public Long getId() {
        return id;
    }

    public Double getCurricularEvaluation() {
        return scores.get(GrantEvaluationCategory.CURRICULAR_EVALUATION);
    }

    public Double getInterview() {
        return scores.get(GrantEvaluationCategory.INTERVIEW);
    }

    public Double getPracticalExercise() {
        return scores.get(GrantEvaluationCategory.PRACTICAL_EXERCISE);
    }

    public void setCurricularEvaluation(Double curricularEvaluation) {
        scores.put(GrantEvaluationCategory.CURRICULAR_EVALUATION, curricularEvaluation);
    }

    public void setInterview(Double interview) {
        scores.put(GrantEvaluationCategory.INTERVIEW, interview);
    }

    public void setPracticalExercise(Double practicalExercise) {
        scores.put(GrantEvaluationCategory.PRACTICAL_EXERCISE, practicalExercise);
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public void verifyInvariants() {
        if (getCurricularEvaluation() == null || getInterview() == null || getPracticalExercise() == null) {
            throw new CMSException(ErrorMessage.EVALUATION_PARAMETERS_CANNOT_BE_EMPTY);
        }

        if (getCurricularEvaluation() < 0 || getCurricularEvaluation() > 20) {
            throw new CMSException(ErrorMessage.EVALUATION_PARAMETERS_OUT_OF_BOUNDS);
        }

        if (getInterview() < 0 || getInterview() > 20) {
            throw new CMSException(ErrorMessage.EVALUATION_PARAMETERS_OUT_OF_BOUNDS);
        }

        if (getPracticalExercise() < 0 || getPracticalExercise() > 20) {
            throw new CMSException(ErrorMessage.EVALUATION_PARAMETERS_OUT_OF_BOUNDS);
        }
    }

    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", curricularEvaluation=" + getCurricularEvaluation() +
                ", interview=" + getInterview() +
                ", practicalExercise=" + getPracticalExercise() +
                ", enrollmentId=" + enrollment.getId() +
                '}';
    }
    
}
