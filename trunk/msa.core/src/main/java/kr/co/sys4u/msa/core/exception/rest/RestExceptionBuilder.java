package kr.co.sys4u.msa.core.exception.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import kr.co.sys4u.msa.core.exception.AlertException;

public class RestExceptionBuilder {
	private HttpClientErrorException cause;
	private List<HttpStatus> httpStatuses = new ArrayList<>();
	private String message;
	private String redirectUrl;
	private Class<? extends AlertException> exceptionClass;
	
	private RestExceptionBuilder(){
		super();
	}
	
	public static RestExceptionBuilder currentException(HttpClientErrorException cause){
		RestExceptionBuilder builder = new RestExceptionBuilder();
		builder.cause = cause;
		return builder;
	}
	
	public RestExceptionBuilder when(HttpStatus httpStatus){
		httpStatuses.add(httpStatus);
		return this;
	}
	
	public RestExceptionBuilder message(String message){
		this.message = message;
		return this;
	}
	
	public RestExceptionBuilder redirectUrl(String redirectUrl){
		this.redirectUrl = redirectUrl;
		return this;
	}
	
	public RestExceptionBuilder thenThrow(Class<? extends AlertException> exceptionClass){
		this.exceptionClass = exceptionClass;
		return this;
	}
	
	public RuntimeException build(){
		HttpStatus currentStatus = this.cause.getStatusCode();
		
		if(httpStatuses.contains(currentStatus)){
			return newException();
		}
		
		return new RestException(message);
	}
	
	private RuntimeException newException(){
		try {
			return exceptionClass.getConstructor(String.class, String.class, Throwable.class).newInstance(message, redirectUrl, cause);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
