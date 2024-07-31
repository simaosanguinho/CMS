package pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain;

import java.util.regex.Pattern;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String istID;

    public Candidate() {
    }

    public Candidate(String name, String email, String istID) {
        this.name = name;
        this.email = email;
        this.istID = istID;
    }

    public Candidate(CandidateDto candidateDto) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIstID(String istID) {
        this.istID = istID;
    }

    private void verifyInvariants() {
        System.out.println(isValidEmail(email));
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

    public static boolean isValidEmail(String email) {

        String emailRegex = "^(?=.{1,256}$)(?=.{1,64}@.{1,255}$)(?=.{1,64}@)([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+.[a-zA-Z]{2,})$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if(!pat.matcher(email).matches()) {
            System.err.println("Email is invalid");
            throw new IllegalArgumentException("Email is invalid");
        }

        return true;
    }

}
