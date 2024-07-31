package pt.ulisboa.tecnico.rnl.dei.dms.exceptions;

public enum ErrorMessage {
    CANDIDATE_EMAIL_ALREADY_EXISTS("There is already a candidate registered with that email."),
    CANDIDATE_NAME_CANNOT_BE_EMPTY("Candidate must have a name."),
    CANDIDATE_EMAIL_CANNOT_BE_EMPTY("Candidate must have an email."),
    CANDIDATE_IST_ID_CANNOT_BE_EMPTY("Candidate must have an IST ID."),
    ;

    public final String label;

    ErrorMessage(String label) {
        this.label = label;
    }
}