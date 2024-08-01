package pt.ulisboa.tecnico.rnl.dei.dms.exceptions;

public enum ErrorMessage {
    CANDIDATE_EMAIL_ALREADY_EXISTS("There is already a candidate registered with that email."),
    CANDIDATE_NAME_CANNOT_BE_EMPTY("Candidate must have a name."),
    CANDIDATE_EMAIL_CANNOT_BE_EMPTY("Candidate must have an email."),
    CANDIDATE_IST_ID_CANNOT_BE_EMPTY("Candidate must have an IST ID."),
    GRANT_START_DATE_CANNOT_BE_EMPTY("Grant must have a start date."),
    GRANT_END_DATE_CANNOT_BE_EMPTY("Grant must have an end date."),
    GRANT_MONTHLY_INCOME_CANNOT_BE_NEGATIVE("Monthly income cannot be negative."),
    GRANT_START_DATE_CANNOT_BE_AFTER_END_DATE("Start date cannot be after end date."),
    GRANT_NOT_FOUND("Grant was not found.")
    ;

    public final String label;

    ErrorMessage(String label) {
        this.label = label;
    }
}