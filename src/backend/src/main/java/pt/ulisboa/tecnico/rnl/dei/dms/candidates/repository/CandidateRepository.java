package pt.ulisboa.tecnico.rnl.dei.dms.candidates.repository;

import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;

@Repository
@Transactional
public interface CandidateRepository extends JpaRepository<Candidate, Long> {}