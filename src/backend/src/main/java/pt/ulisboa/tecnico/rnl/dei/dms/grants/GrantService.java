package pt.ulisboa.tecnico.rnl.dei.dms.grants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.domain.Evaluation;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.repository.EvaluationRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.domain.Grant;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.dto.GrantDto;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.repository.GrantRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository.EnrollmentRepository;

@Service
public class GrantService {

    @Autowired
    private GrantRepository grantRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public GrantDto createGrant(GrantDto grantDto) {

        Grant grant = new Grant(grantDto);
        grantRepository.save(grant);
        return new GrantDto(grant);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<GrantDto> getGrants() {
        return grantRepository.findAll().stream().map(GrantDto::new).collect(Collectors.toList());
    }  


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<GrantDto> updateGrant(GrantDto grantDto) {
        Grant grant = grantRepository.findById(grantDto.getId()).orElseThrow(() -> new CMSException(ErrorMessage.GRANT_NOT_FOUND));
        grant.update(grantDto);
        grantRepository.save(grant);

        return grantRepository.findAll().stream().map(GrantDto::new).collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<GrantDto> deleteGrant(Long id) {
        grantRepository.deleteById(id);
        return grantRepository.findAll().stream().map(GrantDto::new).collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public GrantDto getGrantById(Long id) {

        try {
            Grant grant = grantRepository.findById(id).get();
            return new GrantDto(grant);
        } catch (Exception e) {
            throw new CMSException(ErrorMessage.GRANT_NOT_FOUND);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public GrantDto updateGrantEvaluationWeights(Long id, GrantDto grantDto) {
        Grant grant = grantRepository.findById(id).orElseThrow(() -> new CMSException(ErrorMessage.GRANT_NOT_FOUND));
        grant.updateEvaluationWeights(grantDto);
        grantRepository.save(grant);

        // update the final score of all enrollments
        grant.getEnrollments().forEach(enrollment -> {
            if(!enrollment.isEvaluated()) {
                return;
            }

            Evaluation evaluation = evaluationRepository.findByEnrollmentId(enrollment.getId()).orElseThrow(() -> new CMSException(ErrorMessage.EVALUATION_NOT_FOUND));
           
            List<Double> scores = evaluation.getScores();
            Double finalScore = (scores.get(0) * grant.getCurricularEvaluationWeight())
                + (scores.get(1) * grant.getPracticalExerciseWeight())  + (scores.get(2)* grant.getInterviewWeight());
            enrollment.setFinalScore(finalScore);
            enrollmentRepository.save(enrollment);
        });

        
        return new GrantDto(grant);
    }

}
