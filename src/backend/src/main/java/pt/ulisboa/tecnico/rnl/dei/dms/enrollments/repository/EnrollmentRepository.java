package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository;

import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

import pt.ulisboa.tecnico.rnl.dei.dms. enrollments.domain.Enrollment;

@Repository
@Transactional
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("SELECT e FROM Enrollment e WHERE e.id = :id")
    Optional<Enrollment> findById(Long id);

    @Query("SELECT e FROM Enrollment e WHERE e.candidate.id = :candidateId")
    List<Enrollment> findByCandidateId(Long candidateId);

    @Query("SELECT e FROM Enrollment e WHERE e.grant.id = :grantId")
    List<Enrollment> findByGrantId(Long grantId);
}