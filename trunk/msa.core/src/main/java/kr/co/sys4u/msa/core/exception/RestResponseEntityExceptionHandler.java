package kr.co.sys4u.msa.core.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import kr.co.sys4u.msa.core.exception.rest.RestException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BadRequestException.class, InternalErrorException.class, NoSuchResourceException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException re, WebRequest request) {
        ResponseStatus responseStatus = re.getClass().getAnnotation(ResponseStatus.class);
        HttpStatus httpStatus = responseStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : responseStatus.value();

        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("error-code", httpStatus.value());
        responseObject.put("error-reason", httpStatus.getReasonPhrase());
        responseObject.put("error-message", re.getMessage());

        return handleExceptionInternal(re, responseObject, new HttpHeaders(), httpStatus, request);
    }
    
    @ExceptionHandler(value = {RestException.class})
    public String handleRestException(RestException restException){
    	return "error/500";
    }
}
