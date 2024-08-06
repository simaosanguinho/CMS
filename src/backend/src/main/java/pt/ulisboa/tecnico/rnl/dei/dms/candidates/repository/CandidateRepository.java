package pt.ulisboa.tecnico.rnl.dei.dms.candidates.repository;

import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;

@Repository
@Transactional
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query("SELECT c FROM Candidate c WHERE c.istID = :istID")
    List<Candidate> findByIstID(Long istID);

    @Query("SELECT c FROM Candidate c WHERE c.email = :email")
    List<Candidate> findByEmail(String email);
}