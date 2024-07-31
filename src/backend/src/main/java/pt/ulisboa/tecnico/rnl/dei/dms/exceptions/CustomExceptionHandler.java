package pt.ulisboa.tecnico.rnl.dei.dms.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// https://www.toptal.com/java/spring-boot-rest-api-error-handling

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger myLogger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(CMSException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CMSExceptionDto tutorException(CMSException e) {
        System.out.println("CMSException");
        return new CMSExceptionDto(e);
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CMSExceptionDto randomException(Exception e) {
        myLogger.error(e.getMessage(), e);
        return new CMSExceptionDto(e);
    }
}