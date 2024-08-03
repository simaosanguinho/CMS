package pt.ulisboa.tecnico.rnl.dei.dms.evaluations.domain;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.dto.EvaluationDto;
import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;

@Entity
@Table(name = "evaluations")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "curricular_evaluation")
    private Double curricularEvaluation;

    @Column(name = "interview")
    private Double interview;

    @Column(name = "practical_exercise")
    private Double practicalExercise;

    @OneToOne
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
        return curricularEvaluation;
    }

    public Double getInterview() {
        return interview;
    }

    public Double getPracticalExercise() {
        return practicalExercise;
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

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public void verifyInvariants() {
        if (curricularEvaluation < 0 || curricularEvaluation > 20) {
            throw new CMSException(ErrorMessage.EVALUATION_PARAMETERS_OUT_OF_BOUNDS);
        }
        if (interview < 0 || interview > 20) {
            throw new CMSException(ErrorMessage.EVALUATION_PARAMETERS_OUT_OF_BOUNDS);
        }
        if (practicalExercise < 0 || practicalExercise > 20) {
            throw new CMSException(ErrorMessage.EVALUATION_PARAMETERS_OUT_OF_BOUNDS);
        }
    }

    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", curricularEvaluation=" + curricularEvaluation +
                ", interview=" + interview +
                ", practicalExercise=" + practicalExercise +
                '}';
    }
    
}
