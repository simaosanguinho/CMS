package pt.ulisboa.tecnico.rnl.dei.dms.candidates;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.repository.CandidateRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.CMSException;

import static pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage.*;
@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    
    public CandidateDto createCandidate(CandidateDto candidateDto) {

        if(candidateDto.getName() == null || candidateDto.getName().isEmpty()) {
            throw new CMSException(CANDIDATE_NAME_CANNOT_BE_EMPTY);
        }

        if(candidateDto.getEmail() == null || candidateDto.getEmail().isEmpty()) {
            throw new CMSException(CANDIDATE_EMAIL_CANNOT_BE_EMPTY);
        }

        if(candidateDto.getIstID() == null || candidateDto.getIstID().isEmpty()) {
            throw new CMSException(CANDIDATE_IST_ID_CANNOT_BE_EMPTY);
        }

        // check if email is already in use
        List<Candidate> candidates = candidateRepository.findByEmail(candidateDto.getEmail());
        if (candidates.size() > 0) {
            throw new CMSException(CANDIDATE_EMAIL_ALREADY_EXISTS);
        }
        
        Candidate candidate = new Candidate(candidateDto);
        candidateRepository.save(candidate);

        return new CandidateDto(candidate);
    }

    public List<CandidateDto> getCandidates() {
        return candidateRepository.findAll().stream().map(CandidateDto::new).collect(Collectors.toList());
    }

    public List<CandidateDto> updateCandidate(CandidateDto candidateDto) {
        Candidate candidate = candidateRepository.findById(candidateDto.getId()).get();

        if(candidateDto.getName() == null || candidateDto.getName().isEmpty()) {
            throw new CMSException(CANDIDATE_NAME_CANNOT_BE_EMPTY);
        }

        if(candidateDto.getEmail() == null || candidateDto.getEmail().isEmpty()) {
            throw new CMSException(CANDIDATE_EMAIL_CANNOT_BE_EMPTY);
        }

        if(candidateDto.getIstID() == null || candidateDto.getIstID().isEmpty()) {
            throw new CMSException(CANDIDATE_IST_ID_CANNOT_BE_EMPTY);
        }

        // check if email is already in use
        List<Candidate> candidates = candidateRepository.findByEmail(candidateDto.getEmail());
        System.out.println(candidates);
        if (candidates.size() > 0 && !candidates.get(0).getId().equals(candidateDto.getId())) {
            System.out.println(candidates);
            throw new CMSException(CANDIDATE_EMAIL_ALREADY_EXISTS);
        }

        candidate.setName(candidateDto.getName());
        candidate.setEmail(candidateDto.getEmail());
        candidate.setIstID(candidateDto.getIstID());
        candidateRepository.save(candidate);
        return getCandidates();
    }

    public List<CandidateDto> deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
        return getCandidates();
    }
    
}
