package pt.ulisboa.tecnico.rnl.dei.dms.grants.domain;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.dto.GrantDto;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.DateHandler;
import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "grants")
public class Grant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long monthlyIncome;

    @ManyToMany(mappedBy = "grants")
    private Set<Candidate> candidates;
    public Grant() {
    }


    public Grant(GrantDto grantDto) {
        setStartDate(DateHandler.toLocalDateTime(grantDto.getStartDate()));
        setEndDate(DateHandler.toLocalDateTime(grantDto.getEndDate()));
        setMonthlyIncome(grantDto.getMonthlyIncome());

        verifyInvariants();
    }

    public void update(GrantDto grantDto) {
        setStartDate(DateHandler.toLocalDateTime(grantDto.getStartDate()));
        setEndDate(DateHandler.toLocalDateTime(grantDto.getEndDate()));
        setMonthlyIncome(grantDto.getMonthlyIncome());

        verifyInvariants();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getMonthlyIncome() {
        return monthlyIncome;
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setMonthlyIncome(Long monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    public void addCandidate(Candidate candidate) {
        this.candidates.add(candidate);
    }

    public void removeCandidate(Candidate candidate) {
        this.candidates.remove(candidate);
    }

    @Override
    public String toString() {
        return "Grant{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", monthlyIncome=" + monthlyIncome +
                ", candidates=" + candidates +
                '}';
    }

    private void verifyInvariants() {
        isValidIncome();
        isGrantDurationValid();
    }

    public void isValidIncome() {
        if(this.monthlyIncome < 0) {
            throw new CMSException(ErrorMessage.GRANT_MONTHLY_INCOME_CANNOT_BE_NEGATIVE);
        }
    }

    public void isGrantDurationValid() {
        if(this.startDate.isAfter(this.endDate)) {
            throw new CMSException(ErrorMessage.GRANT_START_DATE_CANNOT_BE_AFTER_END_DATE);
        }
    }
}
