package pt.ulisboa.tecnico.rnl.dei.dms.evaluations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.domain.Evaluation;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.dto.EvaluationDto;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.repository.EvaluationRepository;

@Service
public class EvaluationService {
    
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public EvaluationDto createEvaluation(EvaluationDto evaluationDto) {
        Evaluation evaluation = new Evaluation(evaluationDto);
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


}
