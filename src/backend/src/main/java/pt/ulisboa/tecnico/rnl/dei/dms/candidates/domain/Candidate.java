package pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain;

import java.util.regex.Pattern;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String istID;

    @OneToMany(mappedBy = "candidate")
    private List<Enrollment> enrollments = new ArrayList<>();

    public Candidate() {
    }

    public Candidate(CandidateDto candidateDto) {
        setName(candidateDto.getName());
        setEmail(candidateDto.getEmail());
        setIstID(candidateDto.getIstID());

        verifyInvariants();
    }

    public void update(CandidateDto candidateDto) {
        setName(candidateDto.getName());
        setEmail(candidateDto.getEmail());
        setIstID(candidateDto.getIstID());

        verifyInvariants();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getIstID() {
        return istID;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIstID(String istID) {
        this.istID = istID;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
    }

    private void verifyInvariants() {
        isValidEmail();
        isValidName();
        isValidIstID();
        
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", istID='" + istID + '\'' +
                '}';
    }

    public void isValidName() {
        if(this.name == null || this.name.isEmpty()) {
            System.err.println("Name is invalid");
            throw new CMSException(ErrorMessage.CANDIDATE_NAME_CANNOT_BE_EMPTY);
        }
    }


    public void isValidEmail() {

        if(this.email == null || this.email.isEmpty()) {
            System.err.println("Email is invalid");
            throw new CMSException(ErrorMessage.CANDIDATE_EMAIL_CANNOT_BE_EMPTY);
        }

        String emailRegex = "^(?=.{1,256}$)(?=.{1,64}@.{1,255}$)(?=.{1,64}@)([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+.[a-zA-Z]{1,})$";

        Pattern pat = Pattern.compile(emailRegex);
        
        if(!pat.matcher(this.email).matches()) {
            System.err.println("Email is invalid");
            throw new CMSException(ErrorMessage.CANDIDATE_EMAIL_IS_INVALID);
        }
    }

    public void isValidIstID() {
        if(this.istID == null || this.istID.isEmpty()) {
            System.err.println("IST ID is invalid");
            throw new CMSException(ErrorMessage.CANDIDATE_IST_ID_CANNOT_BE_EMPTY);
        }
    }

    public Candidate orElseThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
    }

}
