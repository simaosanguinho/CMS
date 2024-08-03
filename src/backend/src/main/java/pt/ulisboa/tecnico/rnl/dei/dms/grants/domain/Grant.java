package pt.ulisboa.tecnico.rnl.dei.dms.grants.domain;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.dto.GrantDto;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.DateHandler;
import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "grants")
public class Grant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long monthlyIncome;

    private Integer vacancy;

    @OneToMany(mappedBy = "grant", cascade = CascadeType.REMOVE)
    private List<Enrollment> enrollments = new ArrayList<>();

    public Grant() {
    }


    public Grant(GrantDto grantDto) {
        setStartDate(DateHandler.toLocalDateTime(grantDto.getStartDate()));
        setEndDate(DateHandler.toLocalDateTime(grantDto.getEndDate()));
        setMonthlyIncome(grantDto.getMonthlyIncome());
        setVacancy(grantDto.getVacancy());

        verifyInvariants();
    }

    public void update(GrantDto grantDto) {
        setStartDate(DateHandler.toLocalDateTime(grantDto.getStartDate()));
        setEndDate(DateHandler.toLocalDateTime(grantDto.getEndDate()));
        setMonthlyIncome(grantDto.getMonthlyIncome());
        setVacancy(grantDto.getVacancy());

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

    public Integer getVacancy() {
        return vacancy;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
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


    public void setVacancy(Integer vacancy) {
        this.vacancy = vacancy;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
    }
    

    @Override
    public String toString() {
        return "Grant{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", monthlyIncome=" + monthlyIncome +
                ", vacancy=" + vacancy +
                '}';
    }

    private void verifyInvariants() {
        isValidIncome();
        isStartDateValid();
        isEndDateValid();
        isGrantDurationValid();
        isVancancyValid();
    }

    public void isValidIncome() {
        if(this.monthlyIncome == null) {
            throw new CMSException(ErrorMessage.GRANT_MONTHLY_INCOME_CANNOT_BE_EMPTY);
        }

        if(this.monthlyIncome < 0) {
            throw new CMSException(ErrorMessage.GRANT_MONTHLY_INCOME_CANNOT_BE_NEGATIVE);
        }
    }

    public void isStartDateValid() {
        if(this.startDate == null) {
            throw new CMSException(ErrorMessage.GRANT_START_DATE_CANNOT_BE_EMPTY);
        }
    }

    public void isEndDateValid() {
        if(this.endDate == null) {
            throw new CMSException(ErrorMessage.GRANT_END_DATE_CANNOT_BE_EMPTY);
        }
    }

    public void isGrantDurationValid() {
        if(this.startDate.isAfter(this.endDate)) {
            throw new CMSException(ErrorMessage.GRANT_START_DATE_CANNOT_BE_AFTER_END_DATE);
        }
    }

    public void isVancancyValid() {
        if(this.vacancy == null || this.vacancy == 0) {
            throw new CMSException(ErrorMessage.GRANT_VACANCY_CANNOT_BE_EMPTY);
        }

        if(this.vacancy < 0) {
            throw new CMSException(ErrorMessage.GRANT_VACANCY_CANNOT_BE_NEGATIVE);
        }
    }
}
