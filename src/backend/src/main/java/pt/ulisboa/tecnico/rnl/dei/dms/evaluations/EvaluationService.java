package pt.ulisboa.tecnico.rnl.dei.dms.evaluations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.domain.Evaluation;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.dto.EvaluationDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.repository.EvaluationRepository;

import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.CMSException;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public EvaluationDto createEvaluation(Long enrollmentId, EvaluationDto evaluationDto) {

        if(evaluationDto == null) {
            throw new CMSException(ErrorMessage.EVALUATION_CANNOT_BE_EMPTY);
        }

        if(enrollmentId == null) {
            throw new CMSException(ErrorMessage.EVALUATION_ENROLLMENT_CANNOT_BE_EMPTY);
        }

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new CMSException(ErrorMessage.ENROLLMENT_NOT_FOUND));

        Evaluation evaluation = new Evaluation(enrollment, evaluationDto);
        evaluationRepository.save(evaluation);
        return new EvaluationDto(evaluation);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void getEvaluationById(Long id) {
        evaluationRepository.findById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public EvaluationDto updateEvaluation(Long id, EvaluationDto evaluationDto) {
        Evaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new CMSException(ErrorMessage.EVALUATION_NOT_FOUND));
        evaluation.update(evaluationDto);
        evaluationRepository.save(evaluation);
        return new EvaluationDto(evaluation);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public EvaluationDto getEvaluationByEnrollmentId(Long enrollmentId) {
        Evaluation evaluation = evaluationRepository.findByEnrollmentId(enrollmentId)
                .orElseThrow(() -> new CMSException(ErrorMessage.EVALUATION_NOT_FOUND));

        System.out.println(evaluation);
        return new EvaluationDto(evaluation);
    }

}
