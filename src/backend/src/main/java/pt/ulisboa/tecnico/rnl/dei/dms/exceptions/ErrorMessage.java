package pt.ulisboa.tecnico.rnl.dei.dms.exceptions;

public enum ErrorMessage {
    EMAIL_ALREADY_EXISTS("Email already exists"),
    
    ;

    public final String label;

    ErrorMessage(String label) {
        this.label = label;
    }
}