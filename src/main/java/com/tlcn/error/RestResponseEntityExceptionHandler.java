package com.tlcn.error;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tlcn.util.GenericResponse;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
    private MessageSource messages;

    public RestResponseEntityExceptionHandler() {
        super();
    }
    
    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("400 Status Code", ex);
        final BindingResult result = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(result.getFieldErrors(), result.getGlobalErrors());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("400 Status Code", ex);
        final BindingResult result = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(result.getFieldErrors(), result.getGlobalErrors());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler({ InvalidOldPasswordException.class })
    public ResponseEntity<Object> handleInvalidOldPassword(final RuntimeException ex, final WebRequest request) {
        logger.error("400 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.invalidOldPassword", null, request.getLocale()), "InvalidOldPassword");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler({NotOwnerOfProposalException.class})
    public ResponseEntity<Object> handleNotOwnerOfThisProposal(final RuntimeException ex, final WebRequest request){
    	logger.error("403 Status Code", ex);
    	final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.notOwnerOfThisProposal", null, request.getLocale()), "NotOwnerOfThisProposal");
    	return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }
    
    @ExceptionHandler({ ProposalNotFoundException.class })
    public ResponseEntity<Object> handleProposalNotFound(final RuntimeException ex, final WebRequest request){
    	logger.error("404 Status Code", ex);
    	final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.proposalNotFound", null, request.getLocale()), "ProposalNotFound");
    	return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    @ExceptionHandler({ DriverNotFoundException.class })
    public ResponseEntity<Object> handleDriverNotFound(final RuntimeException ex, final WebRequest request){
    	logger.error("404 Status Code", ex);
    	final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.driverNotFound", null, request.getLocale()), "DriverNotFound");
    	return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    @ExceptionHandler({ CarNotFoundException.class })
    public ResponseEntity<Object> handleCarNotFound(final RuntimeException ex, final WebRequest request){
    	logger.error("404 Status Code", ex);
    	final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.carNotFound", null, request.getLocale()), "CarNotFound");
    	return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    @ExceptionHandler({ UserNotFoundException.class })
    public ResponseEntity<Object> handleUserNotFound(final RuntimeException ex, final WebRequest request){
    	logger.error("404 Status Code", ex);
    	final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.userNotFound", null, request.getLocale()), "UserNotFound");
    	return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    @ExceptionHandler({ HaveProposalInTimeUseException.class })
    public ResponseEntity<Object> handleHaveProposalInTimeUse(final RuntimeException ex, final WebRequest request){
    	logger.error("405 Status Code", ex);
    	final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.haveProposalInTimeUse", null, request.getLocale()), "haveProposalInTimeUse");
    	return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);
    }
    
    @ExceptionHandler({IOException.class, MultipartException.class})
    public ResponseEntity<Object> handleErrorProcessingFile(final RuntimeException ex, final WebRequest request){
    	logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.ErrorProcessingFile", null, request.getLocale()), "ErrorProcessingFile");
        return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.error", null, request.getLocale()), "InternalError");
        return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
