package pt.ulisboa.tecnico.rnl.dei.dms.exceptions;

public enum ErrorMessage {
    EMAIL_ALREADY_EXISTS("There is already a candidate registered with that email."),
    
    ;

    public final String label;

    ErrorMessage(String label) {
        this.label = label;
    }
}