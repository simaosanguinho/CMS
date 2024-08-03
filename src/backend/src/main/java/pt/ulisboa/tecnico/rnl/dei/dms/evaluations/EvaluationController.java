package pt.ulisboa.tecnico.rnl.dei.dms.evaluations;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.dto.EvaluationDto;

@RestController
@RequestMapping
public class EvaluationController {
    
    @Autowired
    EvaluationService evaluationService;

    @PostMapping("/enrollments/{enrollmentId}/evaluations/update")
    public EvaluationDto updateEvaluation(@PathVariable Long enrollmentId, @RequestBody EvaluationDto evaluationDto) {
        return evaluationService.updateEvaluation(enrollmentId, evaluationDto);
    }

    @GetMapping("/enrollments/{enrollmentId}/evaluations")
    public EvaluationDto getEvaluation(@PathVariable Long enrollmentId) {
        return evaluationService.getEvaluationByEnrollmentId(enrollmentId);
    }   
}
