package pt.ulisboa.tecnico.rnl.dei.dms.grants.repository;

import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

import pt.ulisboa.tecnico.rnl.dei.dms.grants.domain.Grant;

@Repository
@Transactional
public interface GrantRepository extends JpaRepository<Grant, Long> {

    @Query("SELECT g FROM Grant g WHERE g.id = :id")
    Optional<Grant> findById(Long id);

    @Query("SELECT g FROM Grant g WHERE g.startDate = :startDate")
    List<Grant> findByStartDate(String startDate);

    @Query("SELECT g FROM Grant g WHERE g.endDate = :endDate")
    List<Grant> findByEndDate(String endDate);
}