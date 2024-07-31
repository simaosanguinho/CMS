package pt.ulisboa.tecnico.rnl.dei.dms.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

interface CMSExceptionSubError extends Serializable {
}

public class CMSExceptionDto implements CMSExceptionSubError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timestamp;

    private String message;

    private String debugMessage;

    private List<CMSExceptionSubError> subErrors;


    CMSExceptionDto(Throwable ex) {
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public CMSExceptionDto(CMSException e) {
        this.message = e.getMessage();
    }

    public CMSExceptionDto(ErrorMessage errorMessage) {
        this.message = errorMessage.label;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<CMSExceptionSubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<CMSExceptionSubError> subErrors) {
        this.subErrors = subErrors;
    }
}