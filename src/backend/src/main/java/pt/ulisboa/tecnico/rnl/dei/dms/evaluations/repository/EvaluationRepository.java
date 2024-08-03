package pt.ulisboa.tecnico.rnl.dei.dms.evaluations.repository;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluations.domain.Evaluation;


@Repository
@Transactional
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query("SELECT e FROM Evaluation e WHERE e.id = :id")
    Optional<Evaluation> findById(Long id);

    @Query("SELECT e FROM Evaluation e WHERE e.enrollment.id = :enrollmentId")
    Optional<Evaluation> findByEnrollmentId(Long enrollmentId);

}