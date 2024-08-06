package pt.ulisboa.tecnico.rnl.dei.dms.grants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.util.HashMap;
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
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.repository.CandidateRepository;
@Service
public class GrantService {

    @Autowired
    private GrantRepository grantRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CandidateRepository candidateRepository;

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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<CandidateDto> getGrantees(Long id) {
        Grant grant = grantRepository.findById(id).orElseThrow(() -> new CMSException(ErrorMessage.GRANT_NOT_FOUND));

        if (!grant.isOnGoing()) {
            // get the id of the grantees, fetch the candidates and return them
            List<CandidateDto> grantees = grant.getGrantees().stream()
                .map(candidateRepository::findById)
                .map(candidate -> new CandidateDto(candidate.get()))
                .collect(Collectors.toList());
            return grantees;
        }

        if (grant.getEnrollments().isEmpty()) {
            throw new CMSException(ErrorMessage.GRANT_HAS_NO_ENROLLMENTS);
        }

        HashMap<CandidateDto, Double> finalScores = new HashMap<>();

        grant.getEnrollments().forEach(enrollment -> {
            
            if(!enrollment.isEvaluated()) {
                throw new CMSException(ErrorMessage.GRANT_HAS_NON_EVALUATED_ENROLLMENTS);
            }

            CandidateDto candidateDto = new CandidateDto(enrollment.getCandidate());
            finalScores.put(candidateDto, enrollment.getFinalScore());
        });

        grant.setOnGoing(false);

        List<CandidateDto> grantees = finalScores.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .map(e -> e.getKey())
            .collect(Collectors.toList());

        if (grantees.size() > grant.getVacancy()) {
            grantees = grantees.subList(0, grant.getVacancy());
        }


        grant.setGrantees(grantees.stream().map(CandidateDto::getId).collect(Collectors.toList()));

        grantRepository.save(grant);

        // delete all evaluations of the grant - had to be done at the end
        grant.getEnrollments().forEach(enrollment -> {
            Evaluation evaluation = evaluationRepository.findByEnrollmentId(enrollment.getId()).orElseThrow(() -> new CMSException(ErrorMessage.EVALUATION_NOT_FOUND));
            evaluationRepository.delete(evaluation);
        });

        return grantees;

    }   

}
