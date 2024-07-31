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
        Candidate candidate = new Candidate(candidateDto);

        // check if email is already in use
        List<Candidate> candidates = candidateRepository.findByEmail(candidate.getEmail());
        if (candidates.size() > 0) {
            throw new CMSException(EMAIL_ALREADY_EXISTS);
        }

        candidateRepository.save(candidate);

        return new CandidateDto(candidate);
    }

    public List<CandidateDto> getCandidates() {
        return candidateRepository.findAll().stream().map(CandidateDto::new).collect(Collectors.toList());
    }

    public List<CandidateDto> updateCandidate(CandidateDto candidateDto) {
        Candidate candidate = candidateRepository.findById(candidateDto.getId()).get();

        // check if email is already in use
        List<Candidate> candidates = candidateRepository.findByEmail(candidate.getEmail());
        if (candidates.size() > 0) {
            throw new CMSException(EMAIL_ALREADY_EXISTS);
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
