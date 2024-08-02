package pt.ulisboa.tecnico.rnl.dei.dms.exceptions;

public enum ErrorMessage {
    CANDIDATE_EMAIL_ALREADY_EXISTS("There is already a candidate registered with that email."),
    CANDIDATE_IST_ID_ALREADY_EXISTS("There is already a candidate registered with that IST ID."),
    CANDIDATE_NAME_CANNOT_BE_EMPTY("Candidate must have a name."),
    CANDIDATE_EMAIL_CANNOT_BE_EMPTY("Candidate must have an email."),
    CANDIDATE_EMAIL_IS_INVALID("Candidate email is invalid."),
    CANDIDATE_IST_ID_CANNOT_BE_EMPTY("Candidate must have an IST ID."),
    CANDIDATE_NOT_FOUND("Candidate was not found."),
    GRANT_START_DATE_CANNOT_BE_EMPTY("Grant must have a start date."),
    GRANT_END_DATE_CANNOT_BE_EMPTY("Grant must have an end date."),
    GRANT_MONTHLY_INCOME_CANNOT_BE_NEGATIVE("Monthly income cannot be negative."),
    GRANT_MONTHLY_INCOME_CANNOT_BE_EMPTY("Monthly income cannot be null."),
    GRANT_START_DATE_CANNOT_BE_AFTER_END_DATE("Start date cannot be after end date."),
    GRANT_NOT_FOUND("Grant was not found."),
    GRANT_VACANCY_CANNOT_BE_EMPTY("Grant must have a vacancy."),
    GRANT_VACANCY_CANNOT_BE_NEGATIVE("Grant vacancy cannot be negative."),
    ENROLLMENT_CANDIDATE_CANNOT_BE_EMPTY("Candidate cannot be empty."),
    ENROLLMENT_GRANT_CANNOT_BE_EMPTY("Grant cannot be empty."),
    ENROLLMENT_CANNOT_BE_EMPTY("Enrollment cannot be empty."),
    ;

    public final String label;

    ErrorMessage(String label) {
        this.label = label;
    }
}